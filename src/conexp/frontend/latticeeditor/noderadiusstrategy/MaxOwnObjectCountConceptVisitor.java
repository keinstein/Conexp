/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.latticeeditor.noderadiusstrategy;

import conexp.core.Concept;

class MaxOwnObjectCountConceptVisitor extends MaxParamValueConceptVisitor {

    protected int calcCurrentValue(Concept node) {
        return node.getOwnObjCnt();
    }
}
