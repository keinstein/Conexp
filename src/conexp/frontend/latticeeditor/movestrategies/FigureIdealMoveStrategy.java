/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor.movestrategies;

import canvas.Figure;
import canvas.IFigurePredicate;
import conexp.core.ConceptIterator;
import conexp.core.LatticeElement;
import conexp.core.LatticeElementCollection;
import conexp.core.Set;
import conexp.core.enumerators.ConceptIdealIterator;
import conexp.frontend.latticeeditor.LatticeCanvas;
import conexp.frontend.latticeeditor.MoveStrategy;
import conexp.frontend.latticeeditor.figures.AbstractConceptCorrespondingFigure;
import conexp.frontend.latticeeditor.figures.ConceptFigure;

public class FigureIdealMoveStrategy implements MoveStrategy {
    public void moveFigure(LatticeCanvas canvas, canvas.Figure f, double dx, double dy) {
        if (f instanceof AbstractConceptCorrespondingFigure) {
            ConceptFigure conceptFigure = (ConceptFigure) f;

            dy = constraintMinimalUpMoveSizeForIdeal(canvas, conceptFigure, dy);

            ConceptIdealIterator idealIterator = new ConceptIdealIterator(conceptFigure.getConcept());
            while (idealIterator.hasNext()) {
                AbstractConceptCorrespondingFigure nextFigure = canvas.getFigureForConcept(idealIterator.nextConcept());
                nextFigure.moveBy(dx, dy);
            }

        } else {
            f.moveBy(dx, dy);
        }
    }

    public static double constraintMinimalUpMoveSizeForIdeal(LatticeCanvas canvas, ConceptFigure conceptFigure, double initialMoveSize) {
        //todo: minimal size constraint should take into account only the constraints for figures, that lay outside ideal
        //todo: write test and fix this mistake
        if (initialMoveSize >= 0) {
            return initialMoveSize;
        }

        ConceptIdealIterator idealIterator = new ConceptIdealIterator(conceptFigure.getConcept());
        final Set conceptFigureIntent = conceptFigure.getConceptQuery().getQueryIntent();
        IFigurePredicate figureOutsideTheIdeal = new FigureOutsideTheIdealPredicate(conceptFigureIntent);
        double ret = initialMoveSize;
        while (idealIterator.hasNext()) {
            final LatticeElement concept = idealIterator.nextConcept();
            if (existsParentOutsideIdeal(concept.getParents(), conceptFigureIntent)) {
                AbstractConceptCorrespondingFigure nextFigure = canvas.getFigureForConcept(concept);
                double upMoveConstraintForConcept = canvas.getUpMoveConstraintForConcept(nextFigure, figureOutsideTheIdeal);
                ret = -Math.min(upMoveConstraintForConcept, -ret);
            }
        }
        return ret;
    }

    private static boolean existsParentOutsideIdeal(LatticeElementCollection parents, Set conceptFigureIntent) {
        for (ConceptIterator iterator = parents.iterator(); iterator.hasNext();) {
            LatticeElement concept = iterator.nextConcept();
            if (!laysInIdealOf(conceptFigureIntent, concept.getAttribs())) {
                return true;
            }
        }
        return false;
    }

    private static boolean laysInIdealOf(Set conceptFigureIntent, final Set attribs) {
        return conceptFigureIntent.isSubsetOf(attribs);
    }

    private static class FigureOutsideTheIdealPredicate implements IFigurePredicate {
        private final Set conceptFigureIntent;

        public FigureOutsideTheIdealPredicate(Set conceptFigureIntent) {
            this.conceptFigureIntent = conceptFigureIntent;
        }

        public boolean accept(Figure figure) {
            if (figure instanceof ConceptFigure) {
                ConceptFigure otherFigure = (ConceptFigure) figure;
                if (laysInIdealOf(conceptFigureIntent, otherFigure.getConceptQuery().getQueryIntent())) {
                    return false;
                }
                return true;
            }
            return false;
        }
    }
}
