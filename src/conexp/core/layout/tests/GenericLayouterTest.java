/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/

package conexp.core.layout.tests;

import conexp.core.Lattice;
import conexp.core.layout.GenericLayouter;
import conexp.core.layout.Layouter;
import conexp.core.tests.SetBuilder;
import conexp.frontend.latticeeditor.DefaultDrawParams;
import conexp.frontend.latticeeditor.LatticePainterDrawParams;
import junit.framework.TestCase;
import util.testing.SimpleMockPropertyChangeListener;

import java.awt.geom.Point2D;


public abstract class GenericLayouterTest extends TestCase {

    protected boolean isPureImprovingLayout() {
        return false;
    }

    /**
     * Insert the method's description here.
     * Creation date: (30.03.01 3:40:20)
     * @return boolean
     */
    protected abstract boolean isTestImproveOnce();

    /**
     * Insert the method's description here.
     * Creation date: (30.03.01 3:20:03)
     * @return conexp.core.layout.GenericLayouter
     */
    protected abstract GenericLayouter makeLayouter();

    public void testLayoutChangedListener() {
        GenericLayouter layouter = makeLayouter();
        Lattice lat = SetBuilder.makeLatticeWithContext(new int[][]{{1, 0, 0},
                                                                    {1, 1, 0},
                                                                    {0, 0, 1}});
        layouter.initLayout(lat, new DefaultDrawParams());
        SimpleMockPropertyChangeListener listener = new SimpleMockPropertyChangeListener(Layouter.LAYOUT_CHANGE);
        layouter.addLayoutChangeListener(listener);

        if (!isPureImprovingLayout()) {

            listener.setExpected(1);
            layouter.calcInitialPlacement();
            listener.verify();


            listener.setExpected(1);
            layouter.initLayout(lat, new LatticePainterDrawParams());
            layouter.performLayout();
            listener.verify();
        }

        if (isTestImproveOnce()) {
            listener.setExpected(1);
            layouter.improveOnce();
            listener.verify();
        }
    }

    public void testCoordinatesAssignment() {
        if (!isPureImprovingLayout()) {
            GenericLayouter layouter = makeLayouter();
            Lattice lat = SetBuilder.makeLatticeWithContext(new int[][]{{0}});
            layouter.initLayout(lat, new DefaultDrawParams());
            layouter.performLayout();

            Point2D coordsZero = new Point2D.Double();
            layouter.setCoordsForConcept(lat.getZero(), coordsZero);

            Point2D coordsOne = new Point2D.Double();
            layouter.setCoordsForConcept(lat.getOne(), coordsOne);

            assertTrue("Zero node should have bigger coordinates, than one", coordsZero.getY() > coordsOne.getY());

        }
    }

    public void testCoordinatesAssignmentForOneNodeLattice() {
        if (!isPureImprovingLayout()) {
            GenericLayouter layouter = makeLayouter();
            Lattice lat = SetBuilder.makeLatticeWithContext(new int[][]{{1}});
            assertEquals(lat.getZero(), lat.getOne());
            layouter.initLayout(lat, new DefaultDrawParams());
            layouter.performLayout();
            Point2D coordsZero = new Point2D.Double();
            layouter.setCoordsForConcept(lat.getZero(), coordsZero);
            checkCoordsAreNotNaN(coordsZero);
        }
    }

    protected void checkCoordsAreNotNaN(Point2D point) {
        assertEquals(false, Double.isNaN(point.getX()));
        assertEquals(false, Double.isNaN(point.getY()));
    }
}
