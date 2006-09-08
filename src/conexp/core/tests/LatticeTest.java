/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.tests;

import conexp.core.Context;
import conexp.core.Lattice;
import conexp.core.LatticeElement;
import conexp.core.calculationstrategies.DepthSearchCalculator;
import junit.framework.TestCase;


public class LatticeTest extends TestCase {
    private Lattice lat;

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
        assertSame(lat.getOne(), lat.findLatticeElementFromOne(SetBuilder.makeSet(new int[]{0, 0, 0})));
        LatticeElement elem = SetBuilder.findLatticeElementWithIntent(lat,
                new int[]{0, 1, 1});

        assertSame(elem, lat.findLatticeElementFromOne(SetBuilder.makeSet(new int[]{0, 1, 1})));

        assertSame(lat.getZero(), lat.findLatticeElementFromOne(SetBuilder.makeSet(new int[]{1, 1, 1})));

/*
        assertSame(elem, lat.findLatticeElementFromOne(
                SetBuilder.makeSet(new int[]{0, 0, 1})
        ));
        assertSame(elem, lat.findLatticeElementFromOne(
                SetBuilder.makeSet(new int[]{0, 1, 0})
        ));
*/


    }

    public static void testMakeCopy() {
        Context cxt = SetBuilder.makeContext(new int[][]{{0}});
        Lattice lat = SetBuilder.makeLattice(cxt);
        lat.setFeatureMasks(SetBuilder.makeSet(new int[]{1}), SetBuilder.makeSet(new int[]{1}));
        assertEquals(2, lat.conceptsCount());
        Lattice copy = lat.makeCopy();
        assertEquals(lat.conceptsCount(), copy.conceptsCount());
        assertTrue(lat.isEqual(copy));
        assertNotNull(lat.getContext());
        assertSame(lat.getContext(), copy.getContext());
    }

    public static void testFindLatticeElementForAttr() {
        Context cxt = SetBuilder.makeContext(new int[][]{{1}});
        Lattice lat = SetBuilder.makeLattice(cxt);
        assertEquals(1, lat.conceptsCount());
        assertEquals(lat.getZero(), lat.findLatticeElementForAttr(0));
    }
}
