package conexp.experimenter.experiments;

import conexp.core.ConceptEnumCallback;
import conexp.core.BinaryRelation;
import conexp.core.searchconstraints.MinSupportConstrainer;
import conexp.core.searchconstraints.MaxIntentSizeConstrainer;
import conexp.core.calculationstrategies.DepthSearchCalculatorWithFeatureMask;
import conexp.core.enumcallbacks.ConceptNumCallback;
import conexp.core.enumcallbacks.ConceptNumExperimentCallback;
import conexp.experimenter.framework.MeasurementProtocol;
import conexp.experimenter.framework.MeasurementSet;
import conexp.experimenter.framework.ExperimentRunner;


/**
 * Insert the type's description here.
 * Creation date: (21.07.01 15:33:31)
 * @author Serhiy Yevtushenko
 */
public class ConceptCountWithMaxConceptSizeExperiment extends BaseConceptCalcExperiment {
    public static final String MAX_SIZE = "Max size";
    int maxSize = 0;
    public static final String TIME_PER_CONCEPT = "Time per concept";

    public ConceptCountWithMaxConceptSizeExperiment(int maxSize) {
        super("conexp.core.calculationstrategies.DepthSearchCalculatorWithFeatureMask");
        this.maxSize= maxSize;
    }

    DepthSearchCalculatorWithFeatureMask getNativeStrategy(){
        return (DepthSearchCalculatorWithFeatureMask)strategy;
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
        Long runTime = (Long)results.getMeasurementValue(ExperimentRunner.RUNTIME);
        results.setMeasurement(TIME_PER_CONCEPT, new Double(runTime.doubleValue()/getConceptsCount()));
    }

    protected int getConceptsCount() {
        return ((ConceptNumCallback)coll).getConceptCount();
    }
}