/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor.noderadiusstrategy;

import conexp.core.Concept;
import conexp.core.ContextFunctions;
import conexp.core.ExtendedContextEditingInterface;

class MaxStabilityToDestructionConceptVisitor extends MaxParamValueConceptVisitor {

    protected double calcCurrentValue(Concept node) {
        return ContextFunctions.stabilityToDesctruction(node.getAttribs(), cxt);
    }

    ExtendedContextEditingInterface cxt;

    public MaxStabilityToDestructionConceptVisitor(ExtendedContextEditingInterface cxt) {
        this.cxt = cxt;
    }
}
