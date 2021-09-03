package eu.openanalytics.phaedra.model.v2.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.With;

/**
 * POJO holding all information that is part of the request to execute a script.
 */
@Value
@Builder
@With
@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE) // Jackson deserialize compatibility
public class ScriptExecutionInputDTO {

    String id;
    String script;
    String input;
    String responseTopicSuffix;
    long queueTimestamp;

}

