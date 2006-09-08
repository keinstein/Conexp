/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.core.tests;

import conexp.core.Lattice;
import conexp.core.LatticeAlgorithms;
import conexp.core.LatticeElement;
import conexp.core.Set;
import junit.framework.TestCase;



public class LatticeAlgorithmsTest extends TestCase {
    public static void testFindMinimalElementThatIncludesSet() {
        Lattice lat = SetBuilder.makeLattice(new int[][]{
                {0, 0, 0, 1},
                {0, 0, 1, 1},
                {1, 1, 1, 1}
        });
        LatticeElement start = lat.getZero();
        Set firstIntent = SetBuilder.makeSet(new int[]{0, 0, 0, 1});
        LatticeElement result = LatticeAlgorithms.findBottomUpMinimalElementThatIncludesSet(start, firstIntent);
        assertEquals(firstIntent, result.getAttribs());

        Set secondIntent = SetBuilder.makeSet(new int[]{0, 0, 1, 1});
        result = LatticeAlgorithms.findBottomUpMinimalElementThatIncludesSet(start, secondIntent);
        assertEquals(secondIntent, result.getAttribs());

        Set thirdIntent = SetBuilder.makeSet(new int[]{0, 1, 1, 1});
        result = LatticeAlgorithms.findBottomUpMinimalElementThatIncludesSet(start, thirdIntent);
        assertEquals(SetBuilder.makeSet(new int[]{1, 1, 1, 1}), result.getAttribs());

        Set notInSet = SetBuilder.makeSet(new int[]{1, 0, 0, 0});
        result = LatticeAlgorithms.findBottomUpMinimalElementThatIncludesSet(start, notInSet);
        assertEquals(SetBuilder.makeSet(new int[]{1, 1, 1, 1}), result.getAttribs());
    }

    public void testLatticeWidthUpperBound() {
        Lattice oneElementLattice = SetBuilder.makeLattice(new int[][]{{1}});
        assertEquals(1, LatticeAlgorithms.latticeWidthUpperBound(oneElementLattice));
        Lattice twoElementLattice = SetBuilder.makeLattice(new int[][]{{0}});
        assertEquals(1, LatticeAlgorithms.latticeWidthUpperBound(twoElementLattice));
    }

    public void testLatticeWidthLowerBound() {
        Lattice oneElementLattice = SetBuilder.makeLattice(new int[][]{{1}});
        assertEquals(1, LatticeAlgorithms.latticeWidthLowerBound(oneElementLattice));
        Lattice twoElementLattice = SetBuilder.makeLattice(new int[][]{{0}});
        assertEquals(1, LatticeAlgorithms.latticeWidthLowerBound(twoElementLattice));
    }
}
