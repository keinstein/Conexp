/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor.figures;

import canvas.IHighlightStrategy;
import canvas.figures.TextFigure;
import conexp.core.Set;
import conexp.frontend.latticeeditor.ConceptQuery;
import conexp.frontend.latticeeditor.FigureDimensionCalcStrategy;
import conexp.frontend.latticeeditor.FigureDimensionCalcStrategyProvider;

public abstract class ConceptRelatedTextFigure extends TextFigure implements BorderCalculatingLineDiagramFigure {
    conexp.frontend.latticeeditor.FigureDimensionCalcStrategyProvider figureDimensionProvider;


    protected ConceptQuery concept;

    public void setFigureDimensionCalcStrategyProvider(conexp.frontend.latticeeditor.FigureDimensionCalcStrategyProvider figureDimensionProvider) {
        this.figureDimensionProvider = figureDimensionProvider;
    }

    protected FigureDimensionCalcStrategyProvider getFigureDimensionProvider() {
        return this.figureDimensionProvider;
    }

    protected FigureDimensionCalcStrategy getDimensionCalcStrategy() {
        return getFigureDimensionProvider().getFigureDimensionCalcStrategy();
    }


    public ConceptRelatedTextFigure(ConceptQuery conceptQuery) {
        this.concept = conceptQuery;
    }

    protected boolean shouldHighlight(IHighlightStrategy highlightStrategy) {
        return highlightStrategy.highlightFigure(this);
    }

    public Set getIntentQuery() {
        return concept.getQueryIntent();
    }

    public boolean hasCollision() {
        //collisions are not detected for now
        return false;
    }

    public void setCollision(boolean value) {
        //collisions are not detected for now
    }


}
