package conexp.frontend.latticeeditor.figures;

import canvas.CanvasScheme;
import canvas.figures.ColorTransformerWithFadeOut;
import conexp.core.ItemSet;
import conexp.core.Set;
import conexp.frontend.latticeeditor.LatticeCanvasScheme;

public class EdgeFigure extends LineFigureWithFigureDimensionCalcStrategyProvider {
    public EdgeFigure(AbstractConceptCorrespondingFigure start, AbstractConceptCorrespondingFigure end) {
        super(start, end);
        setColorTransformer(ColorTransformerWithFadeOut.getInstance());
    }

    public Set getStartIntentQuery() {
        return ((AbstractConceptCorrespondingFigure) getStartFigure()).getIntentQuery();
    }

    public Set getEndIntentQuery() {
        return ((AbstractConceptCorrespondingFigure) getEndFigure()).getIntentQuery();
    }

    protected ItemSet getStartConcept() {
        return ((AbstractConceptCorrespondingFigure) getStartFigure()).getConcept();
    }

    protected ItemSet getEndConcept() {
        return ((AbstractConceptCorrespondingFigure) getEndFigure()).getConcept();
    }


    protected float getLineThickness(CanvasScheme opt) {
        if (opt instanceof LatticeCanvasScheme) {
            LatticeCanvasScheme canvasSchema = (LatticeCanvasScheme) opt;
            return canvasSchema.getDrawStrategiesContext().getEdgeSizeCalcStrategy().edgeThickness(getStartConcept(), getEndConcept());
        }
        return super.getLineThickness(opt);
    }
}
