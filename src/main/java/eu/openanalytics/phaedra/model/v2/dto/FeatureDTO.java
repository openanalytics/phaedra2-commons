package eu.openanalytics.phaedra.model.v2.dto;

import eu.openanalytics.phaedra.model.v2.enumeration.FeatureType;
import eu.openanalytics.phaedra.model.v2.enumeration.ScriptLanguage;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import lombok.experimental.NonFinal;

@Value
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE) // Jackson deserialize compatibility
@NonFinal
public class FeatureDTO {
    Long id;
    String name;
    String alias;
    String description;
    String format;
    FeatureType type;
    Integer sequence;
    Long protocolId;
    Long formulaId;
    String trigger;
    ScriptLanguage scriptLanguage;
}
