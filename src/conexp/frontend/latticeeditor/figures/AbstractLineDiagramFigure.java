package conexp.frontend.latticeeditor.figures;

/**
 * AbstractLineDiagramFigure.java
 * Created: Sat Dec 16 17:08:07 2000
 *
 * @author Sergey Yevtushenko
 * @version
 */

import canvas.figures.FigureWithCoords;
import conexp.frontend.latticeeditor.FigureDimensionCalcStrategy;
import conexp.frontend.latticeeditor.FigureDimensionCalcStrategyProvider;

public abstract class AbstractLineDiagramFigure extends FigureWithCoords implements LineDiagramFigure {

    conexp.frontend.latticeeditor.FigureDimensionCalcStrategyProvider figureDimensionProvider;

    public AbstractLineDiagramFigure() {
        this(0, 0);
    }

    public AbstractLineDiagramFigure(double x, double y) {
        super(x, y);
    }

    public void setFigureDimensionCalcStrategyProvider(conexp.frontend.latticeeditor.FigureDimensionCalcStrategyProvider figureDimensionProvider) {
        this.figureDimensionProvider = figureDimensionProvider;
    }

    protected FigureDimensionCalcStrategyProvider getFigureDimensionProvider(){
        return this.figureDimensionProvider;
    }

    protected FigureDimensionCalcStrategy getDimensionCalcStrategy() {
        return getFigureDimensionProvider().getFigureDimensionCalcStrategy();
    }

}