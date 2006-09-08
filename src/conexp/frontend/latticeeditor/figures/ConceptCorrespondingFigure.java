/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor.figures;

import conexp.core.LatticeElement;

public abstract class ConceptCorrespondingFigure extends AbstractConceptCorrespondingFigure {

    private LatticeElement concept;

    public LatticeElement getConcept() {
        return concept;
    }

    protected ConceptCorrespondingFigure(LatticeElement el) {
        super();
        this.concept = el;
    }


}
