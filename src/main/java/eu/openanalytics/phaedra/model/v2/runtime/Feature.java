package eu.openanalytics.phaedra.model.v2.runtime;

import eu.openanalytics.phaedra.model.v2.enumeration.FeatureType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder(toBuilder = true)
@AllArgsConstructor
public class Feature {

    Long id;

    String name;

    String alias;

    String description;

    String format;

    FeatureType type;

    Integer sequence;

    Formula formula;

    List<CalculationInputValue> calculationInputValues;

}
