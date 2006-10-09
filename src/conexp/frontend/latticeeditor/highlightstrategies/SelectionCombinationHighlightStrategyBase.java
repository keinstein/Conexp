package conexp.frontend.latticeeditor.highlightstrategies;

import conexp.frontend.latticeeditor.ConceptHighlightAtomicStrategy;
import conexp.frontend.latticeeditor.ConceptHighlightStrategyCombination;
import util.collection.CollectionFactory;

import java.util.List;

public abstract class SelectionCombinationHighlightStrategyBase implements ConceptHighlightStrategyCombination {
    List/*<ConceptHighlightAtomicStrategy>*/ nodes = CollectionFactory.createDefaultList();

    public void clear() {
        nodes.clear();
    }

    public void addNode(ConceptHighlightAtomicStrategy node) {
        nodes.add(node);
    }
}
