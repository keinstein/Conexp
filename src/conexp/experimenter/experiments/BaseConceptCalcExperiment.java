package conexp.experimenter.experiments;

import conexp.core.BinaryRelationProcessor;
import conexp.core.ConceptCalcStrategy;

/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 * Date: 29/8/2003
 * Time: 19:36:53
 */

public abstract class BaseConceptCalcExperiment extends BaseConceptsExperiment {
    public BaseConceptCalcExperiment(String strategyName) {
        super(strategyName);
    }

    public void perform() {
        ((ConceptCalcStrategy)strategy).calculateConceptSet();
    }

    public BinaryRelationProcessor makeStrategy() {
        return (ConceptCalcStrategy) createClassByName(strategyName);
    }
}
