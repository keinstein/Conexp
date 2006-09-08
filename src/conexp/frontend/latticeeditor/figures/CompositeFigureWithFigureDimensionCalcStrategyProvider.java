/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor.figures;

import canvas.Figure;
import canvas.FigureBlock;
import canvas.figures.CompositeFigure;
import conexp.frontend.latticeeditor.FigureDimensionCalcStrategyProvider;


public class CompositeFigureWithFigureDimensionCalcStrategyProvider extends CompositeFigure implements LineDiagramFigure {

    FigureDimensionCalcStrategyProvider figureDimensionProvider;

    protected FigureDimensionCalcStrategyProvider getFigureDimensionProvider() {
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


    public boolean hasCollision() {
        return false;
    }

    public void setCollision(boolean value) {
        //do nothing for now
        //TODO: think, whether we need something more
    }

}
