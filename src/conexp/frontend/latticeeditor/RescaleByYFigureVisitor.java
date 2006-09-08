/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor;


import conexp.frontend.latticeeditor.figures.AbstractConceptCorrespondingFigure;
import conexp.frontend.latticeeditor.figures.DefaultFigureVisitor;


public class RescaleByYFigureVisitor extends DefaultFigureVisitor {
    private int minDelta;
    private int latticeHeight;
    private int shift;

    public RescaleByYFigureVisitor(int shift, int minDelta, int latticeHeight) {
        this.minDelta = minDelta;
        this.latticeHeight = latticeHeight;
        this.shift = shift;
    }

    public void visitConceptCorrespondingFigure(AbstractConceptCorrespondingFigure f) {
        int newCoord = shift + (latticeHeight - f.getConcept().getHeight()) * minDelta;
        f.moveBy(0, newCoord - f.getCenterY());
    }
}
