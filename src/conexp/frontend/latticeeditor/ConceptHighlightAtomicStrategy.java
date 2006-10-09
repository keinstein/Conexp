package conexp.frontend.latticeeditor;

import conexp.frontend.latticeeditor.figures.AbstractConceptCorrespondingFigure;

public interface ConceptHighlightAtomicStrategy extends ConceptHighlightStrategy{
    void initFromFigure(AbstractConceptCorrespondingFigure figure);
}
