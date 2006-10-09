package conexp.frontend.latticeeditor.highlightstrategies;

import conexp.core.Set;
import conexp.frontend.latticeeditor.ConceptHighlightAtomicStrategy;
import conexp.frontend.latticeeditor.ConceptHighlightStrategy;

import java.util.Iterator;

public class AndSelectionHighlightStrategy extends SelectionCombinationHighlightStrategyBase {

    public boolean highlightQuery(Set attribs) {
        boolean result = true;
        for (Iterator iterator = nodes.iterator(); iterator.hasNext();) {
            ConceptHighlightAtomicStrategy atomicStrategy = (ConceptHighlightAtomicStrategy) iterator.next();
            result  = result && atomicStrategy.highlightQuery(attribs);
        }
        return result;
    }

    public boolean highlightEdge(Set startAttribs, Set endAttribs) {
        boolean result = true;
        for (Iterator iterator = nodes.iterator(); iterator.hasNext();) {
            ConceptHighlightAtomicStrategy atomicStrategy = (ConceptHighlightAtomicStrategy) iterator.next();
            result = result && atomicStrategy.highlightEdge(startAttribs, endAttribs);
        }
        return result;
    }

    public ConceptHighlightStrategy createNew() {
        return new AndSelectionHighlightStrategy();
    }
}
