/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor.tests;

import canvas.IFigurePredicate;
import canvas.figures.TrueFigurePredicate;
import conexp.core.Lattice;
import conexp.core.LatticeElement;
import conexp.core.layout.ConceptCoordinateMapper;
import conexp.core.layout.layeredlayout.tests.MapBasedConceptCoordinateMapper;
import conexp.core.layout.layeredlayout.tests.TestDataHolder;
import conexp.core.tests.SetBuilder;
import conexp.frontend.components.LatticeComponent;
import conexp.frontend.components.tests.ComponentsObjectMother;
import conexp.frontend.latticeeditor.ConceptSetDrawing;
import conexp.frontend.latticeeditor.LatticeCanvas;
import conexp.frontend.latticeeditor.LatticeDrawing;
import conexp.frontend.latticeeditor.figures.AbstractConceptCorrespondingFigure;
import conexp.frontend.latticeeditor.figures.ConceptFigure;
import conexp.frontend.latticeeditor.queries.tests.ConceptNodeQueryFactory;
import util.testing.TestUtil;

import java.awt.Dimension;

public class LatticeCanvasTest extends junit.framework.TestCase {

    public static void testGetUpMoveConstraintForConcept() {
        LatticeCanvas latCanvas = new LatticeCanvas() {
            protected double findMinimalYDistanceToPredecessorsFiguresCenters(AbstractConceptCorrespondingFigure f, IFigurePredicate includeInComputation) {
                return 10 + 2 * (getDrawParameters()).getMaxNodeRadius();
            }
        };
        double res = latCanvas.getUpMoveConstraintForConcept(new ConceptFigure(ConceptNodeQueryFactory.makeEmpty()), TrueFigurePredicate.getInstance());
        assertEquals(10.0, res, 0.001);
    }

    public static void testGetUpAndDownMoveStrategiesForConcept() {
        LatticeCanvas canvas = buildPreparedCanvas(TestDataHolder.FULL_RELATION_INTERVAL_4, TestDataHolder.LAYOUT_INTERVAL_4_ASSYMETRIC);
        ConceptSetDrawing drawing = canvas.getConceptSetDrawing();
        Lattice lattice = drawing.getLattice();

        LatticeElement concept = lattice.findElementWithIntent(SetBuilder.makeSet(new int[]{1, 1, 0, 0}));
        AbstractConceptCorrespondingFigure figure = drawing.getFigureForConcept(concept);

        double upConstraint = canvas.getUpMoveConstraintForConcept(figure, TrueFigurePredicate.getInstance());
        double downConstraint = canvas.getDownMoveConstraintForConcept((ConceptFigure) figure);
        assertEquals(40.0, upConstraint, TestDataHolder.PRECISION);
        assertEquals(60.0, downConstraint, TestDataHolder.PRECISION);

    }

    public static void testDimensionUpdateDuringResetOfLatticeDrawing() {
        LatticeCanvas canvas = makeCanvas();
        LatticeDrawing drawing = new LatticeDrawing() {
            public Dimension getDimension() {
                return new Dimension(200, 150);
            }
        };

        canvas.setConceptSetDrawing(drawing);
        assertEquals(new Dimension(200, 150), canvas.getSize());
    }

    public static void testClearLatticeDrawing() {
        LatticeDrawing drawing = makePreparedLatticeDrawing(new int[][]{{0},
                {1}});
        LatticeCanvas canvas = makeCanvas();
        canvas.setConceptSetDrawing(drawing);
        canvas.selectFigure(canvas.getFigureForConcept(drawing.getLattice().getZero()));
        assertTrue(canvas.hasSelection());
        canvas.clearConceptSetDrawing();
        assertEquals(false, canvas.hasSelection());
    }

    public static void testResetOfSelectionAfterChangingDrawing() {
        LatticeDrawing drawing = makePreparedLatticeDrawing(new int[][]{{0},
                {1}});

        LatticeCanvas canvas = makeCanvas();
        canvas.setConceptSetDrawing(drawing);

        canvas.selectFigure(canvas.getFigureForConcept(drawing.getLattice().getZero()));
        assertTrue(canvas.hasSelection());
        LatticeDrawing secondDrawing = makePreparedLatticeDrawing(new int[][]{{0}});
        canvas.setConceptSetDrawing(secondDrawing);
        assertEquals(false, canvas.hasSelection());

    }

    private static LatticeCanvas makeCanvas() {
        return new LatticeCanvas();
    }

    private static LatticeDrawing makePreparedLatticeDrawing(final int[][] relation) {
        LatticeDrawing drawing = new LatticeDrawing();
        drawing.setLattice(SetBuilder.makeLatticeWithContext(relation));
        drawing.layoutLattice();
        return drawing;
    }

    public static LatticeCanvas buildPreparedCanvas(final int[][] saturatedRelation, final double[][] layout) {
        LatticeComponent component = ComponentsObjectMother.makeLatticeComponent(saturatedRelation);
        component.calculateLattice();
        Lattice lattice = component.getLattice();
        ConceptCoordinateMapper mapper = MapBasedConceptCoordinateMapper.buildMapperForLattice(lattice, saturatedRelation,
                layout);
        LatticeDrawing drawing = component.getDrawing();
        drawing.setCoordinatesFromMapper(mapper);

        try {
            drawing.getEditableDrawParameters().setNodeMaxRadius(10);
        } catch (java.beans.PropertyVetoException e) {
            TestUtil.reportUnexpectedException(e);
        }
        LatticeCanvas canvas = new LatticeCanvas();
        canvas.setConceptSetDrawing(drawing);
        boolean strategySet = canvas.getPainterOptions().setFigureDrawingStrategy("MaxNodeRadiusCalcStrategy");
        assertTrue(strategySet);
        return canvas;
    }


}
