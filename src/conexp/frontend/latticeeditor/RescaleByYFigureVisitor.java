/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
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
        f.moveBy(0, shift + (latticeHeight - f.getConcept().getHeight()) * minDelta - f.getCenterY());
    }
}
