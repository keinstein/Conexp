/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.experimenter.experiments;

import conexp.core.BinaryRelation;
import conexp.core.BinaryRelationProcessor;
import conexp.experimenter.framework.MeasurementProtocol;
import conexp.experimenter.framework.MeasurementSet;
import util.StringUtil;


public abstract class BaseConceptsExperiment extends BasicExperiment {
    protected BinaryRelationProcessor strategy;
    protected Object coll;
    protected String strategyName;

    protected BaseConceptsExperiment(String strategyName) {
        this.strategyName = strategyName;
    }

    protected abstract void doLocalSetup();


    protected Object getActionObject() {
        return strategy;
    }

    public abstract BinaryRelationProcessor makeStrategy();

    public void setUp(BinaryRelation rel) {
        strategy = makeStrategy();
        setRelationToStrategy(rel);
        doLocalSetup();
    }

    protected void setRelationToStrategy(BinaryRelation rel) {
        strategy.setRelation(rel);
    }

    protected final static String CONCEPT_COUNT = "Concepts count";

    protected abstract int getConceptsCount();

    protected void declareMeasures(MeasurementProtocol protocol) {
        protocol.addMeasurement(makeValidatingMeasurement(CONCEPT_COUNT));
    }

    public void saveResults(MeasurementSet results) {
        results.setMeasurement(CONCEPT_COUNT, getConceptsCount());
    }

    public void tearDown() {
        coll = null;
        strategy.tearDown();
    }

    public String getDescription() {
        return StringUtil.extractClassName(strategyName);
    }


}
