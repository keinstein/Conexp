/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor.figures;

import canvas.IHighlightStrategy;
import canvas.figures.TextFigure;
import conexp.core.Set;
import conexp.frontend.latticeeditor.ConceptQuery;

public abstract class ConceptRelatedTextFigure extends TextFigure implements IConceptRelatedTextFigure {

    protected ConceptQuery concept;

    protected ConceptRelatedTextFigure(ConceptQuery conceptQuery) {
        this.concept = conceptQuery;
    }

    protected boolean shouldHighlight(IHighlightStrategy highlightStrategy) {
        return highlightStrategy.highlightFigure(this);
    }

    public Set getIntentQuery() {
        return concept.getQueryIntent();
    }

}
