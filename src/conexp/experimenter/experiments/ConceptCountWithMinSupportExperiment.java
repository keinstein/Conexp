/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.experimenter.experiments;

import conexp.core.BinaryRelation;
import conexp.core.ConceptEnumCallback;
import conexp.core.calculationstrategies.DepthSearchCalculatorWithFeatureMask;
import conexp.core.enumcallbacks.ConceptNumCallback;
import conexp.core.enumcallbacks.ConceptNumExperimentCallback;
import conexp.core.searchconstraints.MinSupportConstrainer;
import conexp.experimenter.framework.ExperimentRunner;
import conexp.experimenter.framework.MeasurementProtocol;
import conexp.experimenter.framework.MeasurementSet;



public class ConceptCountWithMinSupportExperiment extends BaseConceptCalcExperiment {
    public static final String ABSOLUTE_SUPPORT = "ABSOLUTE SUPPORT";
    public static final String RELATIVE_SUPPORT = "Relative Support";

    double minSupport = 0;
    int absoluteSupport = 0;
    public static final String TIME_PER_CONCEPT = "Time per concept";

    public ConceptCountWithMinSupportExperiment(double minSupport) {
        super("conexp.core.calculationstrategies.DepthSearchCalculatorWithFeatureMask");
        this.minSupport = minSupport;
    }

    DepthSearchCalculatorWithFeatureMask getNativeStrategy() {
        return (DepthSearchCalculatorWithFeatureMask) strategy;
    }

    protected void setRelationToStrategy(BinaryRelation rel) {
        absoluteSupport = (int) Math.ceil(minSupport * rel.getRowCount());
        super.setRelationToStrategy(rel);
    }

    protected void doLocalSetup() {
        ConceptEnumCallback callback = new ConceptNumExperimentCallback();
        getNativeStrategy().setCallback(callback);
        getNativeStrategy().setSearchConstrainter(new MinSupportConstrainer(absoluteSupport));
        coll = callback;
    }


    protected void declareMeasures(MeasurementProtocol protocol) {
        protocol.addMeasurement(makeUsualMeasurement(CONCEPT_COUNT));
        protocol.addMeasurement(makeUsualMeasurement(ABSOLUTE_SUPPORT));
        protocol.addMeasurement(makeUsualMeasurement(RELATIVE_SUPPORT));
        protocol.addMeasurement(makeUsualMeasurement(TIME_PER_CONCEPT));
    }

    public void saveResults(MeasurementSet results) {
        super.saveResults(results);
        results.setMeasurement(ABSOLUTE_SUPPORT, absoluteSupport);
        results.setMeasurement(RELATIVE_SUPPORT, new Double(minSupport));
        Long runTime = (Long) results.getMeasurementValue(ExperimentRunner.RUNTIME);
        results.setMeasurement(TIME_PER_CONCEPT, new Double(runTime.doubleValue() / getConceptsCount()));
    }

    protected int getConceptsCount() {
        return ((ConceptNumCallback) coll).getConceptCount();
    }
}
