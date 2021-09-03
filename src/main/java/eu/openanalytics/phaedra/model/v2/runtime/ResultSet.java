package eu.openanalytics.phaedra.model.v2.runtime;

import eu.openanalytics.phaedra.model.v2.dto.ErrorDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Value;
import lombok.With;
import lombok.experimental.NonFinal;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.List;

@Value
@Builder(toBuilder = true)
@With
@AllArgsConstructor
@NonFinal
public class ResultSet {

    @Id
    Long id;

    Long protocolId;

    Long plateId;

    Long measId;

    LocalDateTime executionStartTimeStamp;

    LocalDateTime executionEndTimeStamp;

    String outcome;

    ErrorHolder errors;

    String errorsText;

    @Data
    @AllArgsConstructor
    public static class ErrorHolder {
        List<ErrorDTO> errors;
    }

}
