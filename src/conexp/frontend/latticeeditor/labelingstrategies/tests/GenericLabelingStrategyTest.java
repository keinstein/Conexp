/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor.labelingstrategies.tests;

import canvas.BaseFigureVisitor;
import canvas.FigureDrawingListener;
import com.mockobjects.ExpectationCounter;
import conexp.core.Context;
import conexp.core.Lattice;
import conexp.core.tests.SetBuilder;
import conexp.frontend.latticeeditor.LatticeDrawing;
import conexp.frontend.latticeeditor.LatticePainterDrawParams;
import conexp.frontend.latticeeditor.figures.AbstractConceptCorrespondingFigure;
import conexp.frontend.latticeeditor.figures.ConceptFigure;
import conexp.frontend.latticeeditor.labelingstrategies.GenericLabelingStrategy;
import conexp.frontend.latticeeditor.queries.ConceptNodeQuery;
import junit.framework.TestCase;

import java.awt.Dimension;


public abstract class GenericLabelingStrategyTest extends TestCase {
    protected GenericLabelingStrategy labelStrategy;
    private Context cxt;

    protected abstract ConceptNodeQuery makeAcceptable();

    /**
     * Insert the method's description here.
     * Creation date: (25.12.00 0:49:41)
     *
     * @return conexp.frontend.latticeeditor.labelingstrategies.GenericLabelingStrategy
     */
    protected abstract GenericLabelingStrategy makeStrategy();

    /**
     * Insert the method's description here.
     * Creation date: (25.12.00 0:48:03)
     */
    protected void setUp() {
        labelStrategy = makeStrategy();
        cxt = SetBuilder.makeContext(new int[][]{{0, 0},
                {0, 0}});
        labelStrategy.setContext(cxt);
    }

    /**
     * Insert the method's description here.
     * Creation date: (25.12.00 19:17:37)
     */
    public void testAccept() {
        assertTrue(labelStrategy.accept(makeAcceptable()));
    }

    static class MockFigureDrawingListener implements FigureDrawingListener {
        ExpectationCounter counter = new ExpectationCounter("DimensionChanged counter");

        boolean expectedCalled = false;
        boolean actualCalled = false;

        public void setExpected(boolean wasCalled) {
            this.expectedCalled = wasCalled;
        }

        public void verify() {
            assertEquals(expectedCalled, actualCalled);
        }

        public void dimensionChanged(Dimension newDim) {
            actualCalled = true;
        }

        public void needUpdate() {
        }
    }

    public void testApplicationOfChanges() {
        Lattice lat = SetBuilder.makeLatticeFromContext(cxt);
        LatticeDrawing drawing = new LatticeDrawing();
        drawing.setLattice(lat);
        MockFigureDrawingListener listener = new MockFigureDrawingListener();
        drawing.addDrawingChangedListener(listener);
        listener.setExpected(true);
        labelStrategy.init(drawing, makeDrawParams());
        listener.verify();
    }


    public void testCleanup() {
        AbstractConceptCorrespondingFigure f =
                new ConceptFigure(makeAcceptable());
        LatticeDrawing drawing = new LatticeDrawing();
        assertTrue("Array should be empty", drawing.isEmpty());
        drawing.addFigure(f);
        BaseFigureVisitor visitor = labelStrategy.makeInitStrategyVisitor(drawing, new LatticePainterDrawParams());
        f.visit(visitor);
        assertEquals("Strategy should add figires", false, drawing.isEmpty());
        assertTrue("Strategy should have connected figires", labelStrategy.hasConnectedObjects());
        visitor = labelStrategy.makeShutDownVisitor(drawing);
        f.visit(visitor);
        drawing.removeFigure(f);
        assertTrue("Strategy should clean up after herself", drawing.isEmpty());
        assertEquals("Strategy should'nt have connected objects after clean up", false, labelStrategy.hasConnectedObjects());
    }

    protected static LatticePainterDrawParams makeDrawParams() {
        return new LatticePainterDrawParams();
    }
}
