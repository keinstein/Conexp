/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.latticeeditor.movestrategies;

import conexp.frontend.latticeeditor.LatticeCanvas;
import conexp.frontend.latticeeditor.MoveStrategy;
import conexp.frontend.latticeeditor.figures.ConceptFigure;
import canvas.IFigurePredicate;
import canvas.Figure;
import canvas.figures.TrueFigurePredicate;

public class OneFigureMoveStrategy implements MoveStrategy {
    public void moveFigure(LatticeCanvas canvas, canvas.Figure f, double dx, double dy) {
        if (f instanceof ConceptFigure) {
            ConceptFigure conceptFigure = (ConceptFigure) f;
            if (dy < 0) {
                dy = -Math.min(canvas.getUpMoveConstraintForConcept(conceptFigure, TrueFigurePredicate.getInstance()), -dy);
            } else {
                dy = Math.min(canvas.getDownMoveConstraintForConcept(conceptFigure), dy);
            }
        }
        f.moveBy(dx, dy);
    }

}
