/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/

package conexp.frontend.latticeeditor.figures;

import canvas.figures.BorderCalculatingFigure;
import canvas.figures.LineFigure;

public class LineFigureWithFigureDimensionCalcStrategyProvider extends LineFigure implements LineDiagramFigure {

    public LineFigureWithFigureDimensionCalcStrategyProvider(BorderCalculatingFigure start, BorderCalculatingFigure end) {
        super(start, end);
    }

    public void setFigureDimensionCalcStrategyProvider(conexp.frontend.latticeeditor.FigureDimensionCalcStrategyProvider figureDimensionProvider) {
        safeSetDimensionCalcStrategyProvider(getStartFigure(), figureDimensionProvider);
        safeSetDimensionCalcStrategyProvider(getEndFigure(), figureDimensionProvider);
    }

    private void safeSetDimensionCalcStrategyProvider(canvas.Figure figure, conexp.frontend.latticeeditor.FigureDimensionCalcStrategyProvider figureDimensionProvider) {
        if (figure instanceof BorderCalculatingLineDiagramFigure) {
            BorderCalculatingLineDiagramFigure lineDiagramFigure = (BorderCalculatingLineDiagramFigure) figure;
            lineDiagramFigure.setFigureDimensionCalcStrategyProvider(figureDimensionProvider);
        }
    }
}
