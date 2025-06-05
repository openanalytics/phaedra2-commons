package eu.openanalytics.phaedra.util.scripting;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.springframework.kafka.core.KafkaTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import eu.openanalytics.phaedra.util.scripting.model.ScriptExecutionRequest;
import eu.openanalytics.phaedra.util.scripting.model.ScriptExecutionResponse;
import eu.openanalytics.phaedra.util.scripting.model.ScriptLanguage;
import lombok.Data;

/**
 * Allows for sync (caller blocking) or async (using a callback) execution of scripts.
 * Important: before this class can be used, a KafkaListener must be configured to 
 * process script execution updates:
 * 
 * <pre>
 * @KafkaListener(topics = ScriptExecutionManager.TOPIC_SCRIPTENGINE_UPDATES, groupId = "myListenerGroupId")
 * public void onScriptExecutionUpdate(String message) {
 *     scriptExecutionManager.notifyKafkaMessageReceived(message);
 * }
 * </pre>
 * 
 */
public class ScriptExecutionManager {

	public static final String TOPIC_SCRIPTENGINE_REQUESTS = "scriptengine-requests";
	public static final String TOPIC_SCRIPTENGINE_UPDATES = "scriptengine-updates";
	
	private ObjectMapper objectMapper;
	private KafkaTemplate<String, Object> kafkaTemplate;
	private Map<String, ScriptExecution> activeExecutions;
	
	public ScriptExecutionManager(ObjectMapper objectMapper, KafkaTemplate<String, Object> kafkaTemplate) {
		this.objectMapper = objectMapper;
		this.kafkaTemplate = kafkaTemplate;
		this.activeExecutions = new ConcurrentHashMap<>();
	}
	
	public void submit(String script, ScriptLanguage language, Object inputData, Consumer<ScriptExecutionResponse> callback) {
		String inputJSON = null;
    	try {
    		inputJSON = objectMapper.writeValueAsString(inputData);
        } catch (JsonProcessingException e) {
        	throw new RuntimeException("Failed to serialize input data", e);
        }
    	
    	ScriptExecutionRequest request = new ScriptExecutionRequest();
    	request.setId(UUID.randomUUID().toString());
    	request.setScript(script);
    	request.setInput(inputJSON);
    	request.setLanguage(language.name());
    	
    	ScriptExecution execution = new ScriptExecution();
    	execution.setRequest(request);
    	execution.setMaxRetryCount(5);
    	execution.setCallback(callback);
    	
    	submit(execution);
	}
	
	public ScriptExecutionResponse submitSync(String script, ScriptLanguage language, Object inputData) {
		Semaphore threadBlocker = new Semaphore(0);
		AtomicReference<ScriptExecutionResponse> responseHolder = new AtomicReference<>();
		
		Consumer<ScriptExecutionResponse> callback = res -> {
			responseHolder.set(res);
			threadBlocker.release(1);
		};
		
		submit(script, language, inputData, callback);
		try { threadBlocker.acquire(1); } catch (InterruptedException e) {}
		return responseHolder.get();
	}
	
	public void notifyKafkaMessageReceived(String message) {
		ScriptExecutionResponse response;
		try {
			response = objectMapper.readValue(message, ScriptExecutionResponse.class);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Failed to parse kafka message as ScriptExecutionOutput", e);
		}
		
		ScriptExecution execution = activeExecutions.get(response.getInputId());
		if (execution == null) return;

		if (response.getStatusCode().canBeRetried() && execution.getCurrentTry() <= execution.getMaxRetryCount()) {
			// Failure but a retry can be attempted
			submit(execution);
		} else {
			// Success or non-retryable failure
			if (execution.getCallback() != null) execution.getCallback().accept(response);
			activeExecutions.remove(execution.getRequest().getId());
		}
	}
	
	private void submit(ScriptExecution execution) {
		execution.setCurrentTry(execution.getCurrentTry() + 1);
		kafkaTemplate.send(TOPIC_SCRIPTENGINE_REQUESTS, null, execution.getRequest());
    	activeExecutions.put(execution.getRequest().getId(), execution);
	}
	
 	@Data
	private static class ScriptExecution {
		ScriptExecutionRequest request;
		ScriptExecutionResponse response;
		int currentTry;
		int maxRetryCount;
		Consumer<ScriptExecutionResponse> callback;
	}
}
