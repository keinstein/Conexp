/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.latticeeditor.movestrategies;

import conexp.core.enumerators.ConceptIdealIterator;
import conexp.core.Set;
import conexp.core.LatticeElementCollection;
import conexp.core.ConceptIterator;
import conexp.core.LatticeElement;
import conexp.frontend.latticeeditor.LatticeCanvas;
import conexp.frontend.latticeeditor.MoveStrategy;
import conexp.frontend.latticeeditor.figures.AbstractConceptCorrespondingFigure;
import conexp.frontend.latticeeditor.figures.ConceptFigure;
import util.Assert;
import canvas.IFigurePredicate;
import canvas.Figure;

import java.util.Iterator;

public class FigureIdealMoveStrategy implements MoveStrategy {
    public void moveFigure(LatticeCanvas canvas, canvas.Figure f, double dx, double dy) {
        if (f instanceof AbstractConceptCorrespondingFigure) {
            ConceptFigure conceptFigure = (ConceptFigure) f;

            if (dy < 0) {
                System.out.println("constrained move: before:"+dy);
                dy = constraintMinimalUpMoveSizeForIdeal(canvas, conceptFigure, dy);
                System.out.println("after "+dy);
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

    public double constraintMinimalUpMoveSizeForIdeal(LatticeCanvas canvas, ConceptFigure conceptFigure, double initialMoveSize) {
        //todo: minimal size constraint should take into account only the constraints for figures, that lay outside ideal
        //todo: write test and fix this mistake
        Assert.isTrue(initialMoveSize<0);
        ConceptIdealIterator idealIterator = new ConceptIdealIterator(conceptFigure.getConcept());
        final Set conceptFigureIntent = conceptFigure.getConceptQuery().getQueryIntent();

        IFigurePredicate figureOutsideTheIdeal = new FigureOutsideTheIdeal(conceptFigureIntent);

        double ret = initialMoveSize;
        while (idealIterator.hasNext()) {
            final LatticeElement concept = idealIterator.nextConcept();
            if(existsParentOutsideIdeal(concept.getPredecessors(), conceptFigureIntent)){
                AbstractConceptCorrespondingFigure nextFigure = canvas.getFigureForConcept(concept);
                double upMoveConstraintForConcept = canvas.getUpMoveConstraintForConcept(nextFigure, figureOutsideTheIdeal);
                System.out.println("upModeConstraint "+upMoveConstraintForConcept+ " for figure "+nextFigure.getConcept());
                ret = -Math.min(upMoveConstraintForConcept, -ret);
            }
        }
        return ret;
    }

    private boolean existsParentOutsideIdeal(LatticeElementCollection predecessors, Set conceptFigureIntent) {
        for (ConceptIterator iterator = predecessors.iterator(); iterator.hasNext();) {
            LatticeElement concept = iterator.nextConcept();
            if(!concept.getAttribs().isSubsetOf(conceptFigureIntent)){
                return true;
            }
        }
        return false;
    }

    private static class FigureOutsideTheIdeal implements IFigurePredicate {
        private final Set conceptFigureIntent;

        public FigureOutsideTheIdeal(Set conceptFigureIntent) {
            this.conceptFigureIntent = conceptFigureIntent;
        }

        public boolean accept(Figure figure) {
            if (figure instanceof ConceptFigure) {
                ConceptFigure otherFigure = (ConceptFigure) figure;
                if(otherFigure.getConceptQuery().getQueryIntent().isSubsetOf(conceptFigureIntent)){
                   return false;
                }
                return true;
            }
            System.out.println("FigureOutsideTheIdeal:returned false by default");
            return false;
        }
    }
}
