/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor.noderadiusstrategy;

import conexp.core.Concept;
import conexp.core.ContextFunctions;
import conexp.core.ExtendedContextEditingInterface;

class MaxStabilityConceptVisitor extends MaxParamValueConceptVisitor {

    protected int calcCurrentValue(Concept node) {
        return ContextFunctions.stability(node.getAttribs(), cxt);
    }

    ExtendedContextEditingInterface cxt;

    public MaxStabilityConceptVisitor(ExtendedContextEditingInterface cxt) {
        this.cxt = cxt;
    }
}
