package conexp.experimenter.experiments;

import conexp.core.BinaryRelationProcessor;
import conexp.experimenter.framework.MeasurementProtocol;
import conexp.experimenter.framework.MeasurementSet;
import conexp.experimenter.experiments.BasicExperiment;
import util.StringUtil;

/**
 * Insert the type's description here.
 * Creation date: (21.07.01 13:46:49)
 * @author
 */
public abstract class BaseConceptsExperiment extends BasicExperiment {
    protected BinaryRelationProcessor strategy;
    protected Object coll;
    protected String strategyName;

    public BaseConceptsExperiment(String strategyName) {
        this.strategyName = strategyName;
    }

    protected abstract void doLocalSetup();


    protected Object getActionObject() {
        return strategy;
    }

    public abstract BinaryRelationProcessor makeStrategy();

    public void setUp(conexp.core.BinaryRelation rel) {
        strategy = makeStrategy();
        setRelationToStrategy(rel);
        doLocalSetup();
    }

    protected void setRelationToStrategy(conexp.core.BinaryRelation rel) {
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