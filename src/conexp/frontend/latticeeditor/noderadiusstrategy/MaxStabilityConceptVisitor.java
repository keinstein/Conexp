/*
 * Created by IntelliJ IDEA.
 * User: Serhiy Yevtushenko
 * Date: Jun 3, 2002
 * Time: 1:46:35 AM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
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
