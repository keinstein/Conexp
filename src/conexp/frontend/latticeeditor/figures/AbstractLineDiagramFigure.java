/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.latticeeditor.figures;


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

    protected FigureDimensionCalcStrategyProvider getFigureDimensionProvider() {
        return this.figureDimensionProvider;
    }

    protected FigureDimensionCalcStrategy getDimensionCalcStrategy() {
        return getFigureDimensionProvider().getFigureDimensionCalcStrategy();
    }

    boolean collision;

    public boolean hasCollision() {
        return collision;
    }

    public void setCollision(boolean collision) {
        this.collision = collision;
    }

}

