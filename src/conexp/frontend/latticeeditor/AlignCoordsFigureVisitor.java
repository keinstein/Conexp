/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor;

import conexp.frontend.latticeeditor.figures.AbstractConceptCorrespondingFigure;

import java.awt.geom.Point2D;

public class AlignCoordsFigureVisitor extends ConceptFigureCoordinatesChangingFigureVisitor {
    protected final int smallGridSize;

    public AlignCoordsFigureVisitor(int smallGridSize) {
        this.smallGridSize = smallGridSize;
    }

    protected double alignToGrid(double val) {
        return (double) ((int) (val + smallGridSize / 2) / smallGridSize * smallGridSize);
    }

    protected Point2D getCoordsForFigure(AbstractConceptCorrespondingFigure f) {
        return new Point2D.Double(alignToGrid(f.getCenterX()), alignToGrid(f.getCenterY()));
    }

}
