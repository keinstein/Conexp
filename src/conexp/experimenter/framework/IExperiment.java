/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.experimenter.framework;

import conexp.core.BinaryRelation;


public interface IExperiment {
    String getDescription();

    void setUp(BinaryRelation rel);

    void perform() throws ExperimentException;

    void tearDown();

    IMeasurementProtocol getMeasurementProtocol();

    void saveResults(MeasurementSet results);

}
