package conexp.experimenter.framework;

import conexp.core.BinaryRelation;

/**
 * Insert the type's description here.
 * Creation date: (10.07.01 7:17:34)
 * @author
 */
public interface IExperiment {
    String getDescription();

    void setUp(BinaryRelation rel);

    void perform();

    void tearDown();

    IMeasurementProtocol getMeasurementProtocol();

    void saveResults(MeasurementSet results);

}