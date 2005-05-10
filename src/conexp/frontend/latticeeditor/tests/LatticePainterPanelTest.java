/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.latticeeditor.tests;

import canvas.CanvasScheme;
import conexp.core.Context;
import conexp.core.Lattice;
import conexp.core.tests.SetBuilder;
import conexp.frontend.LatticeDrawingProvider;
import conexp.frontend.components.LatticeComponent;
import conexp.frontend.latticeeditor.LatticeDrawing;
import conexp.frontend.latticeeditor.LatticePainterOptions;
import conexp.frontend.latticeeditor.LatticePainterPanel;
import conexp.frontend.tests.ResourcesToolbarDefinitionTest;
import junit.framework.TestCase;
import util.testing.TestUtil;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;


public class LatticePainterPanelTest extends TestCase {
    private LatticePainterPanel pan;


    protected void setUp() {
        pan = TestHelper.makeTestableLatticePainterPanel(SetBuilder.makeLattice(new int[][]{{0}}));
    }

    public void testDefaultLatticeComponentPainterOptions() {
        CanvasScheme options = pan.getDrawing().getOptions();
        assertTrue("Expect LatticePainterOptions but was " + options.getClass().getName(), options instanceof LatticePainterOptions);
    }

    public void testPaint() {
        pan.getScreenImage();
    }

    public void testInitPaint() {
        try {
            pan.initPaint();
        } catch (Throwable t) {
            fail("failed to	initPaint");
        }

    }

    public void testSetLatticeDrawing() {
        try {
            LatticeDrawing drawing = new LatticeDrawing();
            drawing.setLattice(SetBuilder.makeLatticeWithContext(new int[][]{{0}}));
            pan.setConceptSetDrawing(drawing);
        } catch (Throwable t) {
            fail("failed to	execute setLattice");
        }
    }


    public void testToolbarInResources() {
        ResourcesToolbarDefinitionTest.testToolbarDefinitionInResources(pan.getResources(), pan.getActionChain());
    }

    public static void testCorrectWorkingOfOptionsChanges() {
        Context cxt = SetBuilder.makeContext(new int[][]{
            {1, 0, 0},
            {0, 1, 0},
            {0, 0, 1}
        });
        LatticeComponent component = TestHelper.makeTestableLatticeComponent(cxt);
        component.calculateAndLayoutLattice();


        final LatticeDrawing drawing = component.getDrawing();
        assertEquals(60, drawing.getDrawParams().getGridSizeY());
        assertEquals(80, drawing.getDrawParams().getGridSizeX());

        Lattice lattice = component.getLattice();
        TestHelper.checkCoordsForIntent(120, 0.0, drawing, lattice, new int[]{0, 0, 0});
        TestHelper.checkCoordsForIntent(40.0, 60.0, drawing, lattice, new int[]{1, 0, 0});
        TestHelper.checkCoordsForIntent(200.0, 60.0, drawing, lattice, new int[]{0, 1, 0});
        TestHelper.checkCoordsForIntent(120.0, 60.0, drawing, lattice, new int[]{0, 0, 1});
        TestHelper.checkCoordsForIntent(120, 120.0, drawing, lattice, new int[]{1, 1, 1});

        LatticePainterPanel panel = new LatticePainterPanel(component);
        panel.initialUpdate();
        try {
            panel.getEditableDrawingParams().setGridSizeY(80);
        } catch (java.beans.PropertyVetoException e) {
            TestUtil.reportUnexpectedException(e);
        }

        TestHelper.checkCoordsForIntent(120, 0.0, drawing, lattice, new int[]{0, 0, 0});
        TestHelper.checkCoordsForIntent(40.0, 80.0, drawing, lattice, new int[]{1, 0, 0});
        TestHelper.checkCoordsForIntent(200.0, 80.0, drawing, lattice, new int[]{0, 1, 0});
        TestHelper.checkCoordsForIntent(120.0, 80.0, drawing, lattice, new int[]{0, 0, 1});
        TestHelper.checkCoordsForIntent(120, 160.0, drawing, lattice, new int[]{1, 1, 1});
    }
}
