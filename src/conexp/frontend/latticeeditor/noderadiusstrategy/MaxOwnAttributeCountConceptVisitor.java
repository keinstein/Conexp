package conexp.frontend.latticeeditor.noderadiusstrategy;

import conexp.core.Concept;

class MaxOwnAttributeCountConceptVisitor extends MaxParamValueConceptVisitor {

    protected double calcCurrentValue(Concept node) {
        return node.getOwnAttrCnt();
    }
}
