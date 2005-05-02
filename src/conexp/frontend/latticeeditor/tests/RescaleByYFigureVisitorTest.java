package conexp.frontend.latticeeditor.tests;

import conexp.core.Context;
import conexp.core.Lattice;
import conexp.core.tests.SetBuilder;
import conexp.frontend.components.LatticeComponent;
import conexp.frontend.latticeeditor.LatticeDrawing;
import conexp.frontend.latticeeditor.RescaleByYFigureVisitor;
import junit.framework.TestCase;

/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 */

public class RescaleByYFigureVisitorTest extends TestCase {
    public static void testRescaleByY() {

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


        RescaleByYFigureVisitor visitor = new RescaleByYFigureVisitor(0, 80, drawing.getNumberOfLevelsInDrawing());
        drawing.visitFiguresAndApplyChanges(visitor);

        TestHelper.checkCoordsForIntent(120, 0.0, drawing, lattice, new int[]{0, 0, 0});
        TestHelper.checkCoordsForIntent(40.0, 80.0, drawing, lattice, new int[]{1, 0, 0});
        TestHelper.checkCoordsForIntent(200.0, 80.0, drawing, lattice, new int[]{0, 1, 0});
        TestHelper.checkCoordsForIntent(120.0, 80.0, drawing, lattice, new int[]{0, 0, 1});
        TestHelper.checkCoordsForIntent(120, 160.0, drawing, lattice, new int[]{1, 1, 1});


    }

}
