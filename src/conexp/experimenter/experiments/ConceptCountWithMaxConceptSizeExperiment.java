/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.experimenter.experiments;

import conexp.core.ConceptEnumCallback;
import conexp.core.calculationstrategies.DepthSearchCalculatorWithFeatureMask;
import conexp.core.enumcallbacks.ConceptNumCallback;
import conexp.core.enumcallbacks.ConceptNumExperimentCallback;
import conexp.core.searchconstraints.MaxIntentSizeConstrainer;
import conexp.experimenter.framework.ExperimentRunner;
import conexp.experimenter.framework.MeasurementProtocol;
import conexp.experimenter.framework.MeasurementSet;



public class ConceptCountWithMaxConceptSizeExperiment extends BaseConceptCalcExperiment {
    public static final String MAX_SIZE = "Max size";
    int maxSize = 0;
    public static final String TIME_PER_CONCEPT = "Time per concept";

    public ConceptCountWithMaxConceptSizeExperiment(int maxSize) {
        super("conexp.core.calculationstrategies.DepthSearchCalculatorWithFeatureMask");
        this.maxSize = maxSize;
    }

    DepthSearchCalculatorWithFeatureMask getNativeStrategy() {
        return (DepthSearchCalculatorWithFeatureMask) strategy;
    }

    protected void doLocalSetup() {
        ConceptEnumCallback callback = new ConceptNumExperimentCallback();
        getNativeStrategy().setCallback(callback);
        getNativeStrategy().setSearchConstrainter(new MaxIntentSizeConstrainer(maxSize));
        coll = callback;
    }


    protected void declareMeasures(MeasurementProtocol protocol) {
        protocol.addMeasurement(makeUsualMeasurement(CONCEPT_COUNT));
        protocol.addMeasurement(makeUsualMeasurement(MAX_SIZE));
        protocol.addMeasurement(makeUsualMeasurement(TIME_PER_CONCEPT));
    }

    public void saveResults(MeasurementSet results) {
        super.saveResults(results);
        results.setMeasurement(MAX_SIZE, maxSize);
        Long runTime = (Long) results.getMeasurementValue(ExperimentRunner.RUNTIME);
        results.setMeasurement(TIME_PER_CONCEPT, new Double(runTime.doubleValue() / getConceptsCount()));
    }

    protected int getConceptsCount() {
        return ((ConceptNumCallback) coll).getConceptCount();
    }
}
