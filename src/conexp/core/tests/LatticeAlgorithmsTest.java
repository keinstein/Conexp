package conexp.core.tests;

import junit.framework.TestCase;
import conexp.core.*;

/*
 * This program is a part of the Darmstadt JSM Implementation.
 *
 * You can redistribute it (modify it, compile it, decompile it, whatever)
 * AMONG THE JSM COMMUNITY. If you plan to use this program outside the
 * community, please notify V.K.Finn (finn@viniti.ru) and the authors.
 *
 * Authors: Peter Grigoriev and Serhiy Yevtushenko
 * E-mail: {peter, sergey}@intellektik.informatik.tu-darmstadt.de
 * 
 * Date: 5/7/2003
 * Time: 19:53:23
 */

public class LatticeAlgorithmsTest extends TestCase {
    public void testFindMinimalElementThatIncludesSet() {
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
}
