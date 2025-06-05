package eu.openanalytics.phaedra.util.scripting.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScriptExecutionResponse {

	String inputId; // Note: "requestId" would be a better name
    String output;
    ResponseStatusCode statusCode;
    String statusMessage;

}