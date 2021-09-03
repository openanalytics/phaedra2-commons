package eu.openanalytics.phaedra.model.v2.dto;

import eu.openanalytics.phaedra.model.v2.enumeration.ResponseStatusCode;
import eu.openanalytics.phaedra.model.v2.validation.OnCreate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.With;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.LocalDateTime;

@Value
@Builder
@With
@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE) // Jackson deserialize compatibility
public class ResultDataDTO {

    @Null(message = "Id must be specified in URL and not repeated in body", groups = OnCreate.class)
    Long id;

    @Null(message = "ResultSetId must be specified in URL and not repeated in body", groups = OnCreate.class)
    Long resultSetId;

    @NotNull(message = "FeatureId is mandatory", groups = {OnCreate.class})
    Long featureId;

    @NotNull(message = "Values is mandatory", groups = {OnCreate.class})
    float[] values;

    @NotNull(message = "StatusCode is mandatory", groups = {OnCreate.class})
    ResponseStatusCode statusCode;

    @NotNull(message = "StatusMessage is mandatory", groups = {OnCreate.class})
    @Length(max = 255, message = "StatusMessage may only contain 255 characters", groups = OnCreate.class)
    String statusMessage;

    @NotNull(message = "ExitCode is mandatory", groups = {OnCreate.class})
    @Min(value = 0, message = "ExitCode must be in the range [0-255]", groups = OnCreate.class)
    @Max(value = 255, message = "ExitCode must be in the range [0-255]", groups = OnCreate.class)
    Integer exitCode;

    @Null(message = "CreatedTimestamp must be null when creating ResultData", groups = {OnCreate.class})
    LocalDateTime createdTimestamp;

}