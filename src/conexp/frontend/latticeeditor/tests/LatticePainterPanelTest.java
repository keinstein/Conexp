/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor.tests;

import canvas.CanvasScheme;
import canvas.FigureDrawing;
import com.mockobjects.ExpectationCounter;
import conexp.core.Lattice;
import conexp.core.tests.SetBuilder;
import conexp.frontend.ContextDocument;
import conexp.frontend.components.LatticeComponent;
import conexp.frontend.components.tests.ComponentsObjectMother;
import conexp.frontend.latticeeditor.LatticeDrawing;
import conexp.frontend.latticeeditor.LatticePainterOptions;
import conexp.frontend.latticeeditor.LatticePainterPanel;
import conexp.frontend.tests.ResourcesToolbarDefinitionTestHelper;
import junit.framework.TestCase;
import util.testing.TestUtil;


public class LatticePainterPanelTest extends TestCase {
    private LatticePainterPanel pan;

    private void usualSetUp() {

        pan = TestHelper.makeTestableLatticePainterPanel(SetBuilder.makeLattice(new int[][]{{0}}));
    }

    public void testDefaultLatticeComponentPainterOptions() {
        usualSetUp();
        CanvasScheme options = pan.getDrawing().getOptions();
        assertTrue("Expect LatticePainterOptions but was " + options.getClass().getName(), options instanceof LatticePainterOptions);
    }

    public void testPaint() {
        usualSetUp();
        pan.getScreenImage();
    }

    public void testInitPaint() {
        usualSetUp();
        try {
            pan.initPaint();
        } catch (Throwable t) {
            fail("failed to	initPaint");
        }

    }

    public void testThatLatticeIsSetOnlyOnce() {
        final ExpectationCounter counter = new ExpectationCounter("Number the drawing is set");
        counter.setExpected(1);

        pan = new LatticePainterPanel() {
            protected void setDrawing(FigureDrawing newDrawing) {
                counter.inc();
                super.setDrawing(newDrawing);
            }
        };

        pan.setLatticeSupplier(TestHelper.makeTestableLatticeProvider(SetBuilder.makeLattice(new int[][]{{0}})));
        pan.initialUpdate();
        counter.verify();
    }

    public void testSetLatticeDrawing() {
        usualSetUp();
        try {
            LatticeDrawing drawing = new LatticeDrawing();
            drawing.setLattice(SetBuilder.makeLatticeWithContext(new int[][]{{0}}));
            pan.setConceptSetDrawing(drawing);
        } catch (Throwable t) {
            fail("failed to	execute setLattice");
        }
    }


    public void testToolbarInResources() {
        usualSetUp();
        ContextDocument doc = new ContextDocument();
        pan.setParentActionMap(doc.getActionChain());    //here the lattice snapshot command is provided
        ResourcesToolbarDefinitionTestHelper.testToolbarDefinitionInResources(LatticePainterPanel.getResources(), pan.getActionChain());
    }

    public static void testCorrectWorkingOfOptionsChanges() {
        LatticeComponent component = ComponentsObjectMother.makeLatticeComponentWithSimpleLayoutEngine(new int[][]{
                {1, 0, 0},
                {0, 1, 0},
                {0, 0, 1}
        });
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

        LatticePainterPanel panel = LatticePainterPanel.createLatticePainterPanel(component);
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
