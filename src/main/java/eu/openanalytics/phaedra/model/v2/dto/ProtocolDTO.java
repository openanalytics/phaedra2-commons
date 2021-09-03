package eu.openanalytics.phaedra.model.v2.dto;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.List;

@Value
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE) // Jackson deserialize compatibility
public class ProtocolDTO {
    Long id;
    String name;
    String description;
    boolean editable;
    boolean inDevelopment;
    String lowWelltype;
    String highWelltype;
    List<FeatureDTO> features;
}
