package conexp.experimenter.experiments;

import conexp.core.ConceptCalcStrategy;
import conexp.core.ConceptEnumCallback;
import conexp.core.enumcallbacks.ConceptNumCallback;
import conexp.core.enumcallbacks.ConceptNumExperimentCallback;


/**
 * Insert the type's description here.
 * Creation date: (21.07.01 15:33:31)
 *
 * @author Serhiy Yevtushenko
 */
public class ConceptCountExperiment extends BaseConceptCalcExperiment {
    public ConceptCountExperiment(String strategyName) {
        super(strategyName);
    }

    protected void doLocalSetup() {
        ConceptEnumCallback callback = new ConceptNumExperimentCallback();
        ((ConceptCalcStrategy) strategy).setCallback(callback);
        coll = callback;
    }

    protected int getConceptsCount() {
        return ((ConceptNumCallback) coll).getConceptCount();
    }
}