package eu.openanalytics.phaedra.model.v2.runtime;

import eu.openanalytics.phaedra.model.v2.enumeration.ResponseStatusCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.With;
import lombok.experimental.NonFinal;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Value
@Builder
@With
@AllArgsConstructor
@NonFinal
public class ResultData {

    @Id
    Long id;

    Long resultSetId;

    Long featureId;

    float[] values;

    ResponseStatusCode statusCode;

    String statusMessage;

    int exitCode;

    LocalDateTime createdTimestamp;

}
