/*
 * $Id$
 * Copyright (c) 2005 realtime (http://www.realtime.dk),
 * All Rights Reserved.
 */
package conexp.frontend.latticeeditor.noderadiusstrategy;

import conexp.core.Concept;

class MaxOwnAttributeCountConceptVisitor extends MaxParamValueConceptVisitor {

    protected double calcCurrentValue(Concept node) {
        return node.getOwnAttrCnt();
    }
}
