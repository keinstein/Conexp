package conexp.frontend.latticeeditor.labelingstrategies;

import conexp.core.BinaryRelationProcessor;
import conexp.core.stability.BruteForceIntegralStabilityCalculator;
import conexp.core.stability.IntegralStabilityCalculator;
import conexp.frontend.latticeeditor.ConceptQuery;

/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 */

public class IntegralStabilityLabelingStrategy extends SubcontextStabilityLabelingStrategyBase {
    public IntegralStabilityLabelingStrategy() {
        super();
    }

    protected BinaryRelationProcessor makeStabilityCalculator() {
        return new BruteForceIntegralStabilityCalculator();
    }

    IntegralStabilityCalculator getIntegralStabilityCalculator(){
        return (IntegralStabilityCalculator)stabilityCalculator;
    }

    protected double getStabilityValue(ConceptQuery conceptQuery) {
        return getIntegralStabilityCalculator().getIntegralStabilityForSet(conceptQuery.getQueryIntent());
    }
}
