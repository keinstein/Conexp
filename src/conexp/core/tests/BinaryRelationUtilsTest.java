/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.tests;

import conexp.core.BinaryRelation;
import conexp.core.BinaryRelationUtils;
import conexp.core.ModifiableBinaryRelation;
import junit.framework.TestCase;


public class BinaryRelationUtilsTest extends TestCase {
    private static final double PRECISION = 0.0001;

    /**
     * Insert the method's description here.
     * Creation date: (04.08.01 8:36:57)
     */
    private static void doTestLexSort(int[][] relToSort, int[][] sortedRel) {
        BinaryRelation toSort = SetBuilder.makeRelation(relToSort);
        BinaryRelation sorted = SetBuilder.makeRelation(sortedRel);
        assertEquals(sorted, BinaryRelationUtils.lexSort(toSort));
    }

    public static void testLexSort() {
        int[][] relToSort = new int[][]{
                {1, 0, 0, 1},
                {1, 1, 0, 0},
                {0, 0, 0, 0},
                {0, 1, 1, 0},
                {0, 0, 0, 1}
        };

        int[][] sortedRel = new int[][]{
                {0, 0, 0, 0},
                {0, 0, 0, 1},
                {0, 1, 1, 0},
                {1, 0, 0, 1},
                {1, 1, 0, 0}
        };

        doTestLexSort(relToSort, sortedRel);
    }


    public static void testOneColSort() {
        int[][] relToSort = new int[][]{
                {1},
                {1},
                {0},
                {0},
                {0}
        };

        int[][] sortedRel = new int[][]{
                {0},
                {0},
                {0},
                {1},
                {1}
        };

        doTestLexSort(relToSort, sortedRel);
    }

    public static void testTransitiveClosure() {
        int[][] relationToCloseDescr = {
                {0, 0, 0, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 1, 0, 0, 0, 0, 0},
                {0, 0, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 1, 0, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 1},
                {0, 1, 0, 0, 0, 0, 0, 0}
        };
        int[][] expClosedRelationDescr = {
                {0, 0, 0, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0, 0, 0},
                {1, 1, 1, 0, 0, 0, 0, 0},
                {1, 0, 1, 0, 0, 0, 0, 0},
                {1, 1, 1, 1, 1, 0, 1, 1},
                {1, 1, 0, 0, 0, 0, 0, 1},
                {1, 1, 0, 0, 0, 0, 0, 0}
        };

        doTestTransitiveClosure(relationToCloseDescr, expClosedRelationDescr);
    }

    private static void doTestTransitiveClosure(int[][] relationToCloseDescr, int[][] expClosedRelationDescr) {
        ModifiableBinaryRelation relationToClose = SetBuilder.makeRelation(relationToCloseDescr);
        BinaryRelation expRelation = SetBuilder.makeRelation(expClosedRelationDescr);
        BinaryRelationUtils.transitiveClosure(relationToClose);
        assertEquals(expRelation, relationToClose);
    }

    public static void testTransitiveClosureForTheWorstCase() {
        int[][] relationToCloseDescr = {{0, 0, 0, 1},
                {0, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 1, 0}};
        int[][] expClosedRelationDescr = {
                {0, 1, 1, 1},
                {0, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 1, 1, 0}
        };

        doTestTransitiveClosure(relationToCloseDescr, expClosedRelationDescr);
    }

    public static void testAverageNumberOfAttributesPerRow() {
        int[][] relationDescr = {{}};
        BinaryRelation relation = SetBuilder.makeRelation(relationDescr);
        double average = BinaryRelationUtils.averageNumberOfAttributesPerObject(relation);
        assertEquals(0, average, PRECISION);

        relationDescr = new int[][]{
                {1, 0},
                {0, 1}
        };
        relation = SetBuilder.makeRelation(relationDescr);
        assertEquals(1, BinaryRelationUtils.averageNumberOfAttributesPerObject(relation), PRECISION);

        relationDescr = new int[][]{
                {1, 1},
                {0, 1}
        };
        relation = SetBuilder.makeRelation(relationDescr);
        assertEquals(1.5, BinaryRelationUtils.averageNumberOfAttributesPerObject(relation), PRECISION);
    }

    public static void testAverageNumberOfObjectsPerAttribute() {
        int[][] relationDescr = {{}};
        BinaryRelation relation = SetBuilder.makeRelation(relationDescr);
        assertEquals(0, BinaryRelationUtils.averageNumberOfObjectsPerAttribute(relation), PRECISION);

        relationDescr = new int[][]{
                {1, 0, 1},
                {0, 0, 1}
        };
        relation = SetBuilder.makeRelation(relationDescr);
        assertEquals(1, BinaryRelationUtils.averageNumberOfObjectsPerAttribute(relation), PRECISION);
    }

    public static void testVarianceOfObjectPerAttribute() {
        int[][] relationDescr = {{}};
        BinaryRelation relation = SetBuilder.makeRelation(relationDescr);
        assertEquals(0, BinaryRelationUtils.varianceOfObjectPerAttribute(relation), PRECISION);
        relationDescr = new int[][]{
                {1, 0, 1},
                {0, 0, 1}
        };
        relation = SetBuilder.makeRelation(relationDescr);
        assertEquals(0.6666, BinaryRelationUtils.varianceOfObjectPerAttribute(relation), PRECISION);
    }

    public static void testVarianceOfAttributesPerObject() {
        int[][] relationDescr = {{}};
        BinaryRelation relation = SetBuilder.makeRelation(relationDescr);
        assertEquals(0, BinaryRelationUtils.varianceOfAttributesPerObjects(relation), PRECISION);
        relationDescr = new int[][]{
                {1, 0, 1},
                {0, 0, 1}
        };
        relation = SetBuilder.makeRelation(relationDescr);
        assertEquals(0.25, BinaryRelationUtils.varianceOfAttributesPerObjects(relation), PRECISION);
    }

    public static void testMax() {
        assertEquals(3, BinaryRelationUtils.max(new int[]{1, 3, 2}));
        assertEquals(3, BinaryRelationUtils.max(new int[]{1, 2, 3}));
        assertEquals(3, BinaryRelationUtils.max(new int[]{3, 2, 1}));


        try {
            BinaryRelationUtils.max(new int[0]);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }
    }

    public static void testMin() {
        assertEquals(1, BinaryRelationUtils.min(new int[]{1, 3, 2}));
        assertEquals(1, BinaryRelationUtils.min(new int[]{3, 2, 1}));
        assertEquals(1, BinaryRelationUtils.min(new int[]{2, 1, 3}));
        try {
            BinaryRelationUtils.min(new int[0]);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }


    }


}
