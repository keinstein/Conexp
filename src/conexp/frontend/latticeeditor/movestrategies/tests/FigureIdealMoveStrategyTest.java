/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.frontend.latticeeditor.movestrategies.tests;

import canvas.figures.TrueFigurePredicate;
import conexp.core.Lattice;
import conexp.core.LatticeElement;
import conexp.core.layout.layeredlayout.tests.TestDataHolder;
import conexp.core.tests.SetBuilder;
import conexp.frontend.latticeeditor.ConceptSetDrawing;
import conexp.frontend.latticeeditor.LatticeCanvas;
import conexp.frontend.latticeeditor.figures.ConceptFigure;
import conexp.frontend.latticeeditor.movestrategies.FigureIdealMoveStrategy;
import conexp.frontend.latticeeditor.tests.LatticeCanvasTest;
import junit.framework.TestCase;


public class FigureIdealMoveStrategyTest extends TestCase {
    public static void testCalculationOfMoveDistance() {
        LatticeCanvas canvas = LatticeCanvasTest.buildPreparedCanvas(TestDataHolder.FULL_RELATION_NOMINAL_2, TestDataHolder.ASYMMETRIC_LAYOUT_NOMINAL_2);
        ConceptSetDrawing drawing = canvas.getConceptSetDrawing();
        Lattice lattice = drawing.getLattice();

        ConceptFigure figure = (ConceptFigure) canvas.getFigureForConcept(lattice.findConceptWithIntent(SetBuilder.makeSet(new int[]{1, 0})));
        assertEquals(150, figure.getCenterY(), TestDataHolder.PRECISION);
        FigureIdealMoveStrategy strategy = new FigureIdealMoveStrategy();
        double constraint = strategy.constraintMinimalUpMoveSizeForIdeal(canvas, figure, -200);
        assertEquals(-80, constraint, TestDataHolder.PRECISION);
    }

    public static void testCalculationOfUpAndDownConstraints() {
        LatticeCanvas canvas = LatticeCanvasTest.buildPreparedCanvas(TestDataHolder.FULL_RELATION_INTERVAL_4, TestDataHolder.LAYOUT_INTERVAL_4_ASSYMETRIC);
        ConceptSetDrawing drawing = canvas.getConceptSetDrawing();
        Lattice lattice = drawing.getLattice();

        LatticeElement concept = lattice.findElementWithIntent(SetBuilder.makeSet(new int[]{1, 1, 0, 0}));
        final ConceptFigure conceptFigure = (ConceptFigure) drawing.getFigureForConcept(concept);

        double upConstraint = canvas.getUpMoveConstraintForConcept(conceptFigure, TrueFigurePredicate.getInstance());
        double downConstraint = canvas.getDownMoveConstraintForConcept(conceptFigure);
        assertEquals(40.0, upConstraint, TestDataHolder.PRECISION);
        assertEquals(60.0, downConstraint, TestDataHolder.PRECISION);


        assertEquals("Up constraint calculation is wrong ", -20, FigureIdealMoveStrategy.constraintMinimalUpMoveSizeForIdeal(canvas, conceptFigure, -20), TestDataHolder.PRECISION);
        assertEquals("Up move is not properly constrained ", -40, FigureIdealMoveStrategy.constraintMinimalUpMoveSizeForIdeal(canvas, conceptFigure, -50), TestDataHolder.PRECISION);

        assertEquals("Down constraint calculation is wrong", 40, FigureIdealMoveStrategy.constraintMinimalUpMoveSizeForIdeal(canvas, conceptFigure, 40), TestDataHolder.PRECISION);
        assertEquals("Down move shouldnot be constrained in this strategy", 70, FigureIdealMoveStrategy.constraintMinimalUpMoveSizeForIdeal(canvas, conceptFigure, 70), TestDataHolder.PRECISION);
    }

}
