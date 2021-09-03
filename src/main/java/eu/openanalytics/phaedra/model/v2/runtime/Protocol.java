package eu.openanalytics.phaedra.model.v2.runtime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.Map;

@Builder(toBuilder = true)
@Value
@AllArgsConstructor
public class Protocol {

    Long id;

    String name;

    String description;

    boolean editable;

    boolean inDevelopment;

    String lowWelltype;

    String highWelltype;

    Map<Integer, Sequence> sequences;
}
