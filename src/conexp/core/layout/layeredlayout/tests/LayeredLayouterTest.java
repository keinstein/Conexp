/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.layout.layeredlayout.tests;

import conexp.core.Context;
import conexp.core.Set;
import conexp.core.layout.GenericLayouter;
import conexp.core.layout.layeredlayout.LayeredLayoter;
import conexp.core.layout.tests.GenericLayouterTest;
import conexp.core.tests.SetBuilder;

public class LayeredLayouterTest extends GenericLayouterTest {
    protected boolean isTestImproveOnce() {
        return false;
    }

    public static void testFindIrreducibleAttributes() {
        Context cxt = SetBuilder.makeContext(new int[][]{
                {1, 0, 0},
                {0, 1, 0},
                {0, 0, 1}
        });
        Set expectedIrreducible = SetBuilder.makeSet(new int[]{1, 1, 1});
        assertEquals(expectedIrreducible, LayeredLayoter.findIrreducibleAttributes(cxt));
        cxt = SetBuilder.makeContext(new int[][]{
                {1, 1, 1},
                {0, 1, 1},
                {0, 0, 0}
        });
        expectedIrreducible = SetBuilder.makeSet(new int[]{1, 1, 0});
        assertEquals(expectedIrreducible, LayeredLayoter.findIrreducibleAttributes(cxt));

        cxt = SetBuilder.makeContext(new int[][]{
                {1, 0, 0, 0, 0},
                {1, 1, 0, 0, 0},
                {1, 0, 1, 0, 0},
                {1, 1, 0, 1, 0},
                {1, 1, 1, 1, 1}
        });
        expectedIrreducible = SetBuilder.makeSet(new int[]{0, 1, 1, 1, 0});
        assertEquals(expectedIrreducible, LayeredLayoter.findIrreducibleAttributes(cxt));
    }


    protected GenericLayouter makeLayouter() {
        return new LayeredLayoter();
    }
}
