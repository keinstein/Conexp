/*
 * User: sergey
 * Date: Jan 27, 2002
 * Time: 7:14:05 PM
 */
package conexp.frontend.latticeeditor.figures;

import canvas.IHighlightStrategy;
import canvas.figures.TextFigure;
import conexp.core.Set;
import conexp.frontend.latticeeditor.ConceptQuery;
import conexp.frontend.latticeeditor.FigureDimensionCalcStrategy;
import conexp.frontend.latticeeditor.FigureDimensionCalcStrategyProvider;

public abstract class ConceptRelatedTextFigure extends TextFigure implements BorderCalculatingLineDiagramFigure{
    conexp.frontend.latticeeditor.FigureDimensionCalcStrategyProvider figureDimensionProvider;


    protected ConceptQuery concept;

    public void setFigureDimensionCalcStrategyProvider(conexp.frontend.latticeeditor.FigureDimensionCalcStrategyProvider figureDimensionProvider) {
        this.figureDimensionProvider = figureDimensionProvider;
    }

    protected FigureDimensionCalcStrategyProvider getFigureDimensionProvider(){
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


}
