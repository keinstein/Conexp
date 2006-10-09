package conexp.frontend.latticeeditor.highlightstrategies;

import conexp.core.Set;
import conexp.frontend.latticeeditor.ConceptHighlightStrategy;
import conexp.frontend.latticeeditor.ConceptHighlightAtomicStrategy;

import java.util.Iterator;

public class OrSelectionHighlightStrategy extends SelectionCombinationHighlightStrategyBase {
    public boolean highlightQuery(Set attribs) {
        boolean result = false;
        for (Iterator iterator = nodes.iterator(); iterator.hasNext();) {
            ConceptHighlightAtomicStrategy atomicStrategy = (ConceptHighlightAtomicStrategy) iterator.next();
            result = result || atomicStrategy.highlightQuery(attribs);
        }
        return result;
    }

    public boolean highlightEdge(Set startAttribs, Set endAttribs) {
        boolean result = false;
        for (Iterator iterator = nodes.iterator(); iterator.hasNext();) {
            ConceptHighlightAtomicStrategy atomicStrategy = (ConceptHighlightAtomicStrategy) iterator.next();
            result = result || atomicStrategy.highlightEdge(startAttribs, endAttribs);
        }
        return result;
    }

    public ConceptHighlightStrategy createNew() {
        return new OrSelectionHighlightStrategy();
    }
}
