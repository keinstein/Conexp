/*
 * User: Serhiy Yevtushenko
 * Date: Jun 3, 2002
 * Time: 1:46:35 AM
 */
package conexp.frontend.latticeeditor.noderadiusstrategy;

import conexp.core.Concept;

class MaxOwnObjectCountConceptVisitor extends MaxParamValueConceptVisitor {

    protected int calcCurrentValue(Concept node) {
        return node.getOwnObjCnt();
    }
}
