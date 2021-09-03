package eu.openanalytics.phaedra.model.v2.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE) // Jackson deserialize compatibility
public class CalculationInputValueDTO {

    Long id;

    Long featureId;

    String sourceMeasColName;
    String sourceFeatureId;
    String variableName;

}
