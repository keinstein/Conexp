/*
 * User: sergey
 * Date: Jan 25, 2002
 * Time: 5:17:58 PM
 */
package conexp.frontend.latticeeditor.figures;

import canvas.Figure;
import canvas.FigureBlock;
import canvas.figures.CompositeFigure;
import conexp.frontend.latticeeditor.FigureDimensionCalcStrategyProvider;


public class CompositeFigureWithFigureDimensionCalcStrategyProvider extends CompositeFigure implements LineDiagramFigure{

    FigureDimensionCalcStrategyProvider figureDimensionProvider;

    protected FigureDimensionCalcStrategyProvider getFigureDimensionProvider(){
        return figureDimensionProvider;
    }

    public void setFigureDimensionProvider(FigureDimensionCalcStrategyProvider figureDimensionProvider) {
        this.figureDimensionProvider = figureDimensionProvider;
    }


    protected void onAddFigure(Figure f) {
        if (f instanceof LineDiagramFigure) {
            LineDiagramFigure lineDiagramFigure = (LineDiagramFigure) f;
            if (null != getFigureDimensionProvider()) {
                lineDiagramFigure.setFigureDimensionCalcStrategyProvider(getFigureDimensionProvider());
            }
        }
    }

    public void setFigureDimensionCalcStrategyProvider(final FigureDimensionCalcStrategyProvider figureDimensionProvider) {
        setFigureDimensionProvider(figureDimensionProvider);
        forEach(new FigureBlock() {
            public void exec(Figure f) {
                if (f instanceof LineDiagramFigure) {
                    LineDiagramFigure lineDiagramFigure = (LineDiagramFigure) f;
                    lineDiagramFigure.setFigureDimensionCalcStrategyProvider(figureDimensionProvider);
                }
            }
        });

    }


}
