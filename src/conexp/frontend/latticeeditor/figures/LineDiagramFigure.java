/*
 * User: sergey
 * Date: Jan 24, 2002
 * Time: 6:40:26 AM
 */
package conexp.frontend.latticeeditor.figures;

import canvas.Figure;
import conexp.frontend.latticeeditor.FigureDimensionCalcStrategyProvider;

public interface LineDiagramFigure extends Figure {

    public void setFigureDimensionCalcStrategyProvider(FigureDimensionCalcStrategyProvider figureDimensionProvider);
}
