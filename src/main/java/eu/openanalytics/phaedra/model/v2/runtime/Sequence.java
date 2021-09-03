package eu.openanalytics.phaedra.model.v2.runtime;

import lombok.Value;

import java.util.List;


@Value
public class Sequence {

    int sequenceNumber;

    List<Feature> features;

}
