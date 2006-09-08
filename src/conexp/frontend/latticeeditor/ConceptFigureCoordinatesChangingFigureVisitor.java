/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor;

import conexp.frontend.latticeeditor.figures.AbstractConceptCorrespondingFigure;
import conexp.frontend.latticeeditor.figures.DefaultFigureVisitor;

import java.awt.geom.Point2D;

public abstract class ConceptFigureCoordinatesChangingFigureVisitor extends DefaultFigureVisitor {

    protected abstract Point2D getCoordsForFigure(AbstractConceptCorrespondingFigure f);

    public void visitConceptCorrespondingFigure(AbstractConceptCorrespondingFigure f) {
        f.setCoords(getCoordsForFigure(f));
    }
}
