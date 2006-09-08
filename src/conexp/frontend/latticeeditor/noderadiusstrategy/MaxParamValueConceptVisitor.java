/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor.noderadiusstrategy;

import conexp.core.Concept;
import conexp.core.ConceptsCollection;

public abstract class MaxParamValueConceptVisitor implements ConceptsCollection.ConceptVisitor {
    private double maxValue = 0;

    public double getMaxValue() {
        return maxValue;
    }

    public void visitConcept(Concept node) {
        double currValue = calcCurrentValue(node);
        if (currValue > maxValue) {
            maxValue = currValue;
        }
    }

    protected abstract double calcCurrentValue(Concept node);
}
