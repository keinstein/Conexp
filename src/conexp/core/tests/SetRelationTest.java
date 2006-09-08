/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.tests;

import conexp.core.BinaryRelation;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


public class SetRelationTest extends TestCase {
    private static final Class THIS = SetRelationTest.class;

    public static Test suite() {
        return new TestSuite(THIS);
    }


    public void testEquals() {
        BinaryRelation rel = SetBuilder.makeRelation(new int[][]{{1, 0, 0},
                {0, 1, 0}});
        BinaryRelation relEquals = SetBuilder.makeRelation(new int[][]{{1, 0, 0},
                {0, 1, 0}});

        BinaryRelation sameSizeNotEquals = SetBuilder.makeRelation(new int[][]{{1, 0, 1},
                {0, 1, 0}});

        BinaryRelation relNotEquals = SetBuilder.makeRelation(new int[][]{{1, 0, 0},
                {0, 1, 0},
                {1, 0, 0},
                {0, 1, 0}});

        assertEquals(false, rel.equals(null));
        assertEquals(false, rel.equals(relNotEquals));
        assertEquals(false, relNotEquals.equals(rel));
        assertEquals(true, rel.equals(rel));
        assertEquals(true, rel.equals(relEquals));
        assertEquals(true, relEquals.equals(rel));
        assertEquals(false, rel.equals(sameSizeNotEquals));
        assertEquals(false, sameSizeNotEquals.equals(rel));

    }

    public static void testSetDimension() {
        SetBuilder.makeRelation(new int[0][0]);
    }
}
