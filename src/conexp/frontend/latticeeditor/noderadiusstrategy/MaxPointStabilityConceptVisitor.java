/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor.noderadiusstrategy;

import conexp.core.Concept;
import conexp.core.stability.PointStabilityCalculator;

class MaxPointStabilityConceptVisitor extends MaxParamValueConceptVisitor {

    protected double calcCurrentValue(Concept node) {
        return pointStabilityCalculator.getPointStabilityOfSet(node.getAttribs());
    }

    PointStabilityCalculator pointStabilityCalculator;

    public MaxPointStabilityConceptVisitor(PointStabilityCalculator pointStabilityCalculator) {
        this.pointStabilityCalculator = pointStabilityCalculator;
    }
}
