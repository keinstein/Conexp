/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.latticeeditor.figures;

import canvas.CanvasScheme;
import canvas.figures.BorderCalculatingFigure;
import canvas.figures.LineFigure;

import java.awt.*;

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

    protected Color getLineColor(CanvasScheme opt) {
        if(hasCollision()){
            return opt.getColorScheme().getCollisionColor();
        }
        return super.getLineColor(opt);
    }

    protected float getLineThickness(CanvasScheme opt) {
        if(hasCollision()){
            return 2.0f;
        }
        return doGetLineThickness(opt);
    }

    protected float doGetLineThickness(CanvasScheme opt) {
        return super.getLineThickness(opt);
    }

    boolean collision;

    public boolean hasCollision() {
        return collision;
    }

    public void setCollision(boolean collision) {
        this.collision = collision;
    }
}
