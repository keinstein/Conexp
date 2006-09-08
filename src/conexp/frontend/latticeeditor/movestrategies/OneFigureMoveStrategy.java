/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor.movestrategies;

import canvas.figures.TrueFigurePredicate;
import conexp.frontend.latticeeditor.LatticeCanvas;
import conexp.frontend.latticeeditor.MoveStrategy;
import conexp.frontend.latticeeditor.figures.ConceptFigure;

public class OneFigureMoveStrategy implements MoveStrategy {
    public void moveFigure(LatticeCanvas canvas, canvas.Figure f, double dx, double dy) {
        if (f instanceof ConceptFigure) {
            ConceptFigure conceptFigure = (ConceptFigure) f;
            dy = calculateYMoveValue(canvas, conceptFigure, dy);
        }
        f.moveBy(dx, dy);
    }

    /**
     * @param dy
     * @param canvas
     * @param conceptFigure
     * @return
     * @test_public
     */


    public static double calculateYMoveValue(LatticeCanvas canvas, ConceptFigure conceptFigure, double dy) {
        if (dy < 0) {
            dy = -Math.min(canvas.getUpMoveConstraintForConcept(conceptFigure, TrueFigurePredicate.getInstance()), -dy);
        } else {
            dy = Math.min(canvas.getDownMoveConstraintForConcept(conceptFigure), dy);
        }
        return dy;
    }

}
