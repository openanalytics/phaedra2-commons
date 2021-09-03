package eu.openanalytics.phaedra.model.v2.dto;

import eu.openanalytics.phaedra.model.v2.enumeration.ResponseStatusCode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.With;

@Value
@Builder
@With
@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE) // Jackson deserialize compatibility
public class ScriptExecutionOutputDTO {

    String inputId;
    String output;
    ResponseStatusCode statusCode;
    String statusMessage;
    int exitCode;

}
