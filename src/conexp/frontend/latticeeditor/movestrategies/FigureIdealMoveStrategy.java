/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/

package conexp.frontend.latticeeditor.movestrategies;

import conexp.core.enumerators.ConceptIdealIterator;
import conexp.frontend.latticeeditor.LatticeCanvas;
import conexp.frontend.latticeeditor.MoveStrategy;
import conexp.frontend.latticeeditor.figures.AbstractConceptCorrespondingFigure;
import conexp.frontend.latticeeditor.figures.ConceptFigure;

public class FigureIdealMoveStrategy implements MoveStrategy {
    public void moveFigure(LatticeCanvas canvas, canvas.Figure f, double dx, double dy) {
        if (f instanceof AbstractConceptCorrespondingFigure) {
            ConceptFigure conceptFigure = (ConceptFigure) f;

            if (dy < 0) {
                dy = findMinimalSizeUpMoveForIdeal(canvas, conceptFigure, dy);
            }

            ConceptIdealIterator idealIterator = new ConceptIdealIterator(conceptFigure.getConcept());
            while (idealIterator.hasNext()) {
                AbstractConceptCorrespondingFigure nextFigure = canvas.getFigureForConcept(idealIterator.nextConcept());
                nextFigure.moveBy(dx, dy);
            }
        } else {
            f.moveBy(dx, dy);
        }
    }

    private double findMinimalSizeUpMoveForIdeal(LatticeCanvas canvas, ConceptFigure conceptFigure, double dy) {
        ConceptIdealIterator idealIterator = new ConceptIdealIterator(conceptFigure.getConcept());
        while (idealIterator.hasNext()) {
            AbstractConceptCorrespondingFigure nextFigure = canvas.getFigureForConcept(idealIterator.nextConcept());
            dy = -Math.min(canvas.getUpMoveConstraintForConcept(nextFigure), -dy);
        }
        return dy;
    }
}
