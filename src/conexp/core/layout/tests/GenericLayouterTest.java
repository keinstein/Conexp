/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.layout.tests;

import conexp.core.Lattice;
import conexp.core.layout.DefaultLayoutParameters;
import conexp.core.layout.GenericLayouter;
import conexp.core.layout.Layouter;
import conexp.core.tests.SetBuilder;
import junit.framework.TestCase;
import util.testing.SimpleMockPropertyChangeListener;
import util.testing.TestUtil;

import java.awt.geom.Point2D;


public abstract class GenericLayouterTest extends TestCase {

    protected boolean isPureImprovingLayout() {
        return false;
    }

    protected abstract boolean isTestImproveOnce();

    protected abstract GenericLayouter makeLayouter();

    public void testLayoutChangedListener() {
        GenericLayouter layouter = makeLayouter();
        Lattice lat = SetBuilder.makeLatticeWithContext(new int[][]{{1, 0, 0},
                {1, 1, 0},
                {0, 0, 1}});
        layouter.initLayout(lat, new DefaultLayoutParameters());
        SimpleMockPropertyChangeListener listener = new SimpleMockPropertyChangeListener(Layouter.LAYOUT_CHANGE);
        layouter.addLayoutChangeListener(listener);

        if (!isPureImprovingLayout()) {

            listener.setExpected(1);
            layouter.calcInitialPlacement();
            listener.verify();


            listener.setExpected(1);
            //layouter.initLayout(lat, new LatticePainterDrawParams());
            layouter.initLayout(lat, new DefaultLayoutParameters());
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
            layouter.initLayout(lat, new DefaultLayoutParameters());
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
            layouter.initLayout(lat, new DefaultLayoutParameters());
            layouter.performLayout();
            Point2D coordsZero = new Point2D.Double();
            layouter.setCoordsForConcept(lat.getZero(), coordsZero);
            checkCoordsAreNotNaN(coordsZero);
        }
    }

    protected static void checkCoordsAreNotNaN(Point2D point) {
        assertEquals(false, Double.isNaN(point.getX()));
        assertEquals(false, Double.isNaN(point.getY()));
    }

    public void testEquals() {
        GenericLayouter first = makeLayouter();
        GenericLayouter second = makeLayouter();
        TestUtil.testEqualsAndHashCode(first, second);
    }
}
