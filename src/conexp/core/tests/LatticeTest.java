/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/

package conexp.core.tests;

import conexp.core.Lattice;
import conexp.core.LatticeElement;
import conexp.core.calculationstrategies.DepthSearchCalculator;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


public class LatticeTest extends TestCase {
    private final static java.lang.Class THIS = LatticeTest.class;
    Lattice lat;

    public static Test suite() {
        return new TestSuite(THIS);
    }

    /**
     * Insert the method's description here.
     * Creation date: (06.03.01 0:29:47)
     */
    public void testTopologicallySorted() {
        lat = SetBuilder.makeLattice(new int[][]{{1, 0, 0},
                                                 {1, 1, 0},
                                                 {1, 1, 1}}, new DepthSearchCalculator());
        assertEquals(3, lat.conceptsCount());

        //special ordering because of calcHeight
        LatticeElement[] topSortOrder = lat.topologicallySortedElements();
//	assertEquals(2, lat.getHeight());

        assertEquals(SetBuilder.makeSet(new int[]{1, 1, 1}), topSortOrder[0].getAttribs());
        assertEquals(SetBuilder.makeSet(new int[]{1, 1, 0}), topSortOrder[1].getAttribs());
        assertEquals(SetBuilder.makeSet(new int[]{1, 0, 0}), topSortOrder[2].getAttribs());
    }

    public void testIsEmpty() {
        lat = new Lattice();
        assertTrue(lat.isEmpty());
    }

    public void testIsEqual() {
        lat = new Lattice();
        Lattice secLattice = new Lattice();
        assertTrue(lat.isEqual(secLattice));
        // @todo write more test, among them handling feature mask
    }


    public void testFindLatticeElementFromOne() {
        lat = SetBuilder.makeLattice(new int[][]{
            {0, 0, 0},
            {0, 1, 1}
        });
        assertSame(lat.getOne(), lat.findLatticeElementFromOne(
                SetBuilder.makeSet(new int[]{0, 0, 0})
        ));
        LatticeElement elem = SetBuilder.findLatticeElementWithIntent(lat,
                new int[]{0, 1, 1}
        );

        assertSame(elem, lat.findLatticeElementFromOne(
                SetBuilder.makeSet(new int[]{0, 1, 1})
        ));

        assertSame(lat.getZero(), lat.findLatticeElementFromOne(
                SetBuilder.makeSet(new int[]{1, 1, 1})
        ));


/*
        assertSame(elem, lat.findLatticeElementFromOne(
                SetBuilder.makeSet(new int[]{0, 0, 1})
        ));
        assertSame(elem, lat.findLatticeElementFromOne(
                SetBuilder.makeSet(new int[]{0, 1, 0})
        ));
*/


    }
}
