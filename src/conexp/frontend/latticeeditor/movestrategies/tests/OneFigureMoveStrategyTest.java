package conexp.frontend.latticeeditor.movestrategies.tests;

import junit.framework.TestCase;
import conexp.frontend.components.LatticeComponent;
import conexp.frontend.latticeeditor.*;
import conexp.frontend.latticeeditor.tests.LatticeCanvasTest;
import conexp.frontend.latticeeditor.movestrategies.OneFigureMoveStrategy;
import conexp.frontend.latticeeditor.figures.AbstractConceptCorrespondingFigure;
import conexp.frontend.latticeeditor.figures.ConceptFigure;
import conexp.core.tests.SetBuilder;
import conexp.core.layout.layeredlayout.tests.TestDataHolder;
import conexp.core.layout.layeredlayout.tests.MapBasedConceptCoordinateMapper;
import conexp.core.layout.ConceptCoordinateMapper;
import conexp.core.Lattice;
import conexp.core.LatticeElement;
import util.testing.TestUtil;
import canvas.figures.TrueFigurePredicate;

/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 * Date: 14/7/2003
 * Time: 22:10:45
 */

public class OneFigureMoveStrategyTest extends TestCase {

    public void testCalculateYMoveValue() throws Exception {
        LatticeCanvas canvas = LatticeCanvasTest.buildPreparedCanvas(TestDataHolder.FULL_RELATION_INTERVAL_4, TestDataHolder.LAYOUT_INTERVAL_4_ASSYMETRIC);
        ConceptSetDrawing drawing = canvas.getConceptSetDrawing();
        Lattice lattice = drawing.getLattice();


        LatticeElement concept = lattice.findElementWithIntent(SetBuilder.makeSet(new int[]{1, 1, 0, 0}));
        final ConceptFigure conceptFigure = (ConceptFigure)drawing.getFigureForConcept(concept);

        double upConstraint = canvas.getUpMoveConstraintForConcept(conceptFigure, TrueFigurePredicate.getInstance());
        double downConstraint = canvas.getDownMoveConstraintForConcept(conceptFigure);
        assertEquals(40.0, upConstraint, TestDataHolder.PRECISION);
        assertEquals(60.0, downConstraint, TestDataHolder.PRECISION);


        assertEquals("Up constraint calculation is wrong ",-20, OneFigureMoveStrategy.calculateYMoveValue(canvas, conceptFigure, -20), TestDataHolder.PRECISION);
        assertEquals("Up move is not properly constrained ",-40, OneFigureMoveStrategy.calculateYMoveValue(canvas, conceptFigure, -50), TestDataHolder.PRECISION);

        assertEquals("Down constraint calculation is wrong", 40, OneFigureMoveStrategy.calculateYMoveValue(canvas, conceptFigure, 40), TestDataHolder.PRECISION);
        assertEquals("Down move is not properly constrained", 60, OneFigureMoveStrategy.calculateYMoveValue(canvas, conceptFigure, 70), TestDataHolder.PRECISION);
    }

}
