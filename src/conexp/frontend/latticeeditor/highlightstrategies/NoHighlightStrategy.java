/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor.highlightstrategies;

import conexp.core.Set;
import conexp.frontend.latticeeditor.ConceptHighlightAtomicStrategy;
import conexp.frontend.latticeeditor.ConceptHighlightStrategy;
import conexp.frontend.latticeeditor.figures.AbstractConceptCorrespondingFigure;


public class NoHighlightStrategy implements ConceptHighlightAtomicStrategy {

    static NoHighlightStrategy INSTANCE = new NoHighlightStrategy();

    private NoHighlightStrategy() {
    }

    public void initFromFigure(AbstractConceptCorrespondingFigure figure) {

    }

    public boolean highlightEdge(Set startAttribs, Set endAttribs) {
        return false;
    }

    public boolean highlightQuery(Set attribs) {
        return false;
    }


    public ConceptHighlightStrategy createNew() {
        return getInstance();
    }


    public static NoHighlightStrategy getInstance() {
        return INSTANCE;
    }


}
