/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.core.tests;

import conexp.core.BinaryRelation;
import conexp.core.BinaryRelationUtils;
import conexp.core.ModifiableBinaryRelation;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


public class BinaryRelationUtilsTest extends TestCase {
    private static final Class THIS = BinaryRelationUtilsTest.class;

    public BinaryRelationUtilsTest(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(THIS);
    }

    /**
     * Insert the method's description here.
     * Creation date: (04.08.01 8:36:57)
     */
    protected void doTestLexSort(int[][] relToSort, int[][] sortedRel) {
        BinaryRelation toSort = SetBuilder.makeRelation(relToSort);
        BinaryRelation sorted = SetBuilder.makeRelation(sortedRel);
        assertEquals(sorted, conexp.core.BinaryRelationUtils.lexSort(toSort));
    }

    public void testLexSort() {
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


    public void testOneColSort() {
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

    public void testTransitiveClosure() {
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

    private void doTestTransitiveClosure(int[][] relationToCloseDescr, int[][] expClosedRelationDescr) {
        ModifiableBinaryRelation relationToClose = SetBuilder.makeRelation(relationToCloseDescr);
        BinaryRelation expRelation = SetBuilder.makeRelation(expClosedRelationDescr);
        BinaryRelationUtils.transitiveClosure(relationToClose);
        assertEquals(expRelation, relationToClose);
    }

    public void testTransitiveClosureForTheWorstCase() {
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
}
