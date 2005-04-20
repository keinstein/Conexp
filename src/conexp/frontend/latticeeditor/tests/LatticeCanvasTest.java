/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.latticeeditor.tests;

import canvas.IFigurePredicate;
import canvas.IHighlightStrategy;
import canvas.CanvasScheme;
import canvas.figures.TrueFigurePredicate;
import conexp.core.Lattice;
import conexp.core.LatticeElement;
import conexp.core.layout.ConceptCoordinateMapper;
import conexp.core.layout.layeredlayout.tests.MapBasedConceptCoordinateMapper;
import conexp.core.layout.layeredlayout.tests.TestDataHolder;
import conexp.core.tests.SetBuilder;
import conexp.frontend.components.LatticeComponent;
import conexp.frontend.latticeeditor.*;
import conexp.frontend.latticeeditor.drawstrategies.DefaultDrawStrategiesModelsFactory;
import conexp.frontend.latticeeditor.figures.AbstractConceptCorrespondingFigure;
import conexp.frontend.latticeeditor.figures.ConceptFigure;
import conexp.frontend.latticeeditor.queries.ConceptNodeQueryFactory;
import util.testing.TestUtil;

import java.awt.*;

public class LatticeCanvasTest extends junit.framework.TestCase {

    public void testGetUpMoveConstraintForConcept() {
        final LatticeCanvasScheme options = new LatticeCanvasScheme() {
            public canvas.CanvasColorScheme getColorScheme() {
                return null;
            }

            public CanvasScheme makeCopy() {
                return null;
            }

            DrawParameters drawParams = BasicDrawParams.getInstance();
            DrawStrategiesContext drawStrategiesContext = new LatticeCanvasDrawStrategiesContext(new DefaultDrawStrategiesModelsFactory(drawParams), null);

            public DrawStrategiesContext getDrawStrategiesContext() {
                return drawStrategiesContext;
            }

            public IHighlightStrategy getHighlightStrategy() {
                return drawStrategiesContext.getHighlightStrategy();
            }

            public Font getLabelsFont(Graphics g) {
                return g.getFont();
            }

            public FontMetrics getLabelsFontMetrics(Graphics g) {
                return g.getFontMetrics(getLabelsFont(g));
            }

        };

        LatticeCanvas latCanvas = new LatticeCanvas(options) {
            protected double findMinimalYDistanceToPredecessorsFiguresCenters(AbstractConceptCorrespondingFigure f, IFigurePredicate includeInComputation) {
                return 10 + 2 * (getDrawParameters()).getMaxNodeRadius();
            }
        };
        double res = latCanvas.getUpMoveConstraintForConcept(new ConceptFigure(
                ConceptNodeQueryFactory.makeEmpty()), TrueFigurePredicate.getInstance());
        assertEquals(10.0, res, 0.001);
    }

    public void testGetUpAndDownMoveStrategiesForConcept() {
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

    public void testDimensionUpdateDuringResetOfLatticeDrawing() {
        LatticeCanvas canvas = makeCanvas();
        LatticeDrawing drawing = new LatticeDrawing() {
            public Dimension getDimension() {
                return new Dimension(200, 150);
            }
        };

        canvas.setConceptSetDrawing(drawing);
        assertEquals(new Dimension(200, 150), canvas.getSize());
    }

    public void testClearLatticeDrawing() {
        LatticeDrawing drawing = makePreparedLatticeDrawing(new int[][]{{0},
                                                                        {1}});
        LatticeCanvas canvas = makeCanvas();
        canvas.setConceptSetDrawing(drawing);
        canvas.selectFigure(canvas.getFigureForConcept(drawing.getLattice().getZero()));
        assertTrue(canvas.hasSelection());
        canvas.clearConceptSetDrawing();
        assertEquals(false, canvas.hasSelection());
    }

    public void testResetOfSelectionAfterChangingDrawing() {
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

    private LatticeCanvas makeCanvas() {
        return new LatticeCanvas(new LatticePainterOptions());
    }

    private LatticeDrawing makePreparedLatticeDrawing(final int[][] relation) {
        LatticeDrawing drawing = new LatticeDrawing();
        drawing.setLattice(SetBuilder.makeLatticeWithContext(relation));
        drawing.layoutLattice();
        return drawing;
    }

    public static LatticeCanvas buildPreparedCanvas(final int[][] saturatedRelation, final double[][] layout) {
        LatticeComponent component = new LatticeComponent(SetBuilder.makeContext(saturatedRelation));
        component.calculateLattice();
        Lattice lattice = component.getLattice();
        ConceptCoordinateMapper mapper = MapBasedConceptCoordinateMapper.buildMapperForLattice(
                lattice, saturatedRelation,
                layout
        );
        LatticeDrawing drawing = component.getDrawing();
        drawing.setCoordinatesFromMapper(mapper);

        try {
            drawing.getLatticeDrawingOptions().getEditableDrawingOptions().setNodeMaxRadius(10);
            //todo: make drawing options scriptable in normal way
        } catch (java.beans.PropertyVetoException e) {
            TestUtil.reportUnexpectedException(e);
        }

        LatticePainterOptions latticeCanvasScheme = new LatticePainterOptions();
        boolean strategySet = latticeCanvasScheme.setFigureDrawingStrategy("MaxNodeRadiusCalcStrategy");
        assertTrue(strategySet);

        LatticeCanvas canvas = new LatticeCanvas(latticeCanvasScheme);
        canvas.setConceptSetDrawing(drawing);
        return canvas;
    }


}
