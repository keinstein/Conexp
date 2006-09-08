/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor.labelingstrategies;

import conexp.core.BinaryRelationProcessor;
import conexp.core.stability.PointAndIntegralStabilityCalculator;
import conexp.core.stability.PointStabilityCalculator;
import conexp.frontend.latticeeditor.ConceptQuery;


public class PointStabilityLabelingStrategy extends SubcontextStabilityLabelingStrategyBase {

    public PointStabilityLabelingStrategy() {
        super();
    }

    protected BinaryRelationProcessor makeStabilityCalculator() {
        return new PointAndIntegralStabilityCalculator();
    }

    protected PointStabilityCalculator getPointStabilityCalculator() {
        return (PointStabilityCalculator) stabilityCalculator;
    }

    protected double getStabilityValue(ConceptQuery conceptQuery) {
        return getPointStabilityCalculator().getPointStabilityOfSet(conceptQuery.getQueryIntent());
    }

}
