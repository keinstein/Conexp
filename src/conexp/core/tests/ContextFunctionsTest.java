/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.tests;

import conexp.core.ContextFunctions;
import conexp.core.ExtendedContextEditingInterface;
import junit.framework.TestCase;


public class ContextFunctionsTest extends TestCase {


    public static void testStability() {
        ExtendedContextEditingInterface cxt = SetBuilder.makeContext(new int[][]{{1, 0, 0},
                {0, 1, 1},
                {0, 1, 1},
                {1, 1, 0},
                {1, 1, 1},
                {1, 1, 1}});
        assertEquals(1, ContextFunctions.stabilityToDesctruction(SetBuilder.makeSet(new int[]{1, 0, 0}), cxt));
        assertEquals(2, ContextFunctions.stabilityToDesctruction(SetBuilder.makeSet(new int[]{0, 1, 1}), cxt));
        assertEquals(1, ContextFunctions.stabilityToDesctruction(SetBuilder.makeSet(new int[]{0, 1, 0}), cxt));
        assertEquals(2, ContextFunctions.stabilityToDesctruction(SetBuilder.makeSet(new int[]{1, 1, 1}), cxt));
    }
}
