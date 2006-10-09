package conexp.frontend.latticeeditor;

public interface ConceptHighlightStrategyCombination extends ConceptHighlightStrategy{
    void clear();

    void addNode(ConceptHighlightAtomicStrategy node);
}
