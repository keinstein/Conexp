/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.latticeeditor.tests;

import canvas.IHighlightStrategy;
import conexp.core.tests.SetBuilder;
import conexp.frontend.latticeeditor.*;
import conexp.frontend.latticeeditor.drawstrategies.DefaultDrawStrategiesModelsFactory;
import conexp.frontend.latticeeditor.figures.AbstractConceptCorrespondingFigure;
import conexp.frontend.latticeeditor.figures.ConceptFigure;
import conexp.frontend.latticeeditor.queries.ConceptNodeQueryFactory;
import junit.framework.Test;
import junit.framework.TestSuite;

import java.awt.Dimension;

public class LatticeCanvasTest extends junit.framework.TestCase {

    public void testGetUpMoveConstraintForConcept() {
        final LatticeCanvasScheme options = new LatticeCanvasScheme() {
                    public canvas.CanvasColorScheme getColorScheme() {
                        return null;
                    }

                    DrawParameters drawParams = new DefaultDrawParams();
                    DrawStrategiesContext drawStrategiesContext = new LatticeCanvasDrawStrategiesContext(new DefaultDrawStrategiesModelsFactory(drawParams), null);

                    public DrawStrategiesContext getDrawStrategiesContext() {
                        return drawStrategiesContext;
                    }

                    public IHighlightStrategy getHighlightStrategy() {
                        return drawStrategiesContext.getHighlightStrategy();
                    }
                };

        LatticeCanvas latCanvas = new LatticeCanvas(options) {
            protected double findMinimalYDistanceToPredecessorsFiguresCenters(AbstractConceptCorrespondingFigure f) {
                return 10 + 2 * (new DefaultDrawParams()).getMaxNodeRadius();
            }
        };
        double res = latCanvas.getUpMoveConstraintForConcept(new ConceptFigure(
                ConceptNodeQueryFactory.makeEmpty()));
        assertEquals(10.0, res, 0.001);

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
        return new LatticeCanvas(new LatticePainterOptions(new DefaultDrawParams()));
    }

    private LatticeDrawing makePreparedLatticeDrawing(final int[][] relation) {
        LatticeDrawing drawing = new LatticeDrawing();
        drawing.setLattice(SetBuilder.makeLatticeWithContext(relation));
        drawing.layoutLattice();
        return drawing;
    }
}
