/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.experimenter.experiments.tests;

import conexp.experimenter.experiments.IntPair2D;
import junit.framework.TestCase;
import util.testing.TestUtil;



public class IntPair2DTest extends TestCase {
    public static void testCompare() {
        IntPair2D first = new IntPair2D(1, 1);
        IntPair2D second = new IntPair2D(1, 1);
        assertEquals(0, first.compareTo(second));
        TestUtil.testEqualsAndHashCode(first, second);
        IntPair2D bigger = new IntPair2D(2, 1);
        assertTrue(first.compareTo(bigger) < 0);
        assertTrue(bigger.compareTo(first) > 0);
        IntPair2D otherBigger = new IntPair2D(1, 2);
        assertTrue(first.compareTo(otherBigger) < 0);
        assertTrue(otherBigger.compareTo(first) > 0);
    }
}
