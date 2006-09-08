/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.experimenter.experiments;

import conexp.core.BinaryRelationProcessor;
import conexp.core.ConceptCalcStrategy;



public abstract class BaseConceptCalcExperiment extends BaseConceptsExperiment {
    protected BaseConceptCalcExperiment(String strategyName) {
        super(strategyName);
    }

    public void perform() {
        ((ConceptCalcStrategy) strategy).calculateConceptSet();
    }

    public BinaryRelationProcessor makeStrategy() {
        return (ConceptCalcStrategy) createClassByName(strategyName);
    }
}
