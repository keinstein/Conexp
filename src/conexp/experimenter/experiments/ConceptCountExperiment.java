/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.experimenter.experiments;

import conexp.core.ConceptCalcStrategy;
import conexp.core.ConceptEnumCallback;
import conexp.core.enumcallbacks.ConceptNumCallback;
import conexp.core.enumcallbacks.ConceptNumExperimentCallback;



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
