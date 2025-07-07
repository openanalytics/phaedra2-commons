package eu.openanalytics.phaedra.util.scripting.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScriptExecutionRequest {

	String id;
	String script;
	String language;
	String input;

}