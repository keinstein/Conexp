/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.latticeeditor.labelingstrategies;

import conexp.frontend.latticeeditor.ConceptQuery;
import conexp.frontend.latticeeditor.DrawParameters;
import conexp.core.BinaryRelationProcessor;
import conexp.core.stability.PointAndIntegralStabilityCalculator;
import conexp.core.stability.PointStabilityCalculator;


public class PointStabilityLabelingStrategy extends SubcontextStabilityLabelingStrategyBase {

    public PointStabilityLabelingStrategy(DrawParameters opt) {
        super(opt);
    }

    protected BinaryRelationProcessor makeStabilityCalculator() {
        return new PointAndIntegralStabilityCalculator();
    }

    protected PointStabilityCalculator getPointStabilityCalculator(){
        return (PointStabilityCalculator)stabilityCalculator;
    }

    protected double getStabilityValue(ConceptQuery conceptQuery) {
        return getPointStabilityCalculator().getPointStabilityOfSet(conceptQuery.getQueryIntent());
    }

}
