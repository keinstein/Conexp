/*
 * User: Serhiy Yevtushenko
 * Date: Jun 3, 2002
 * Time: 1:47:50 AM
 */
package conexp.frontend.latticeeditor.noderadiusstrategy;

import conexp.core.Concept;
import conexp.core.ConceptsCollection;

public abstract class MaxParamValueConceptVisitor implements ConceptsCollection.ConceptVisitor {
    private int maxValue = 0;

    public int getMaxValue() {
        return maxValue;
    }

    public void visitConcept(Concept node) {
        int currValue = calcCurrentValue(node);
        if(currValue>maxValue){
            maxValue = currValue;
        }
    }

    protected abstract int calcCurrentValue(Concept node);
}
