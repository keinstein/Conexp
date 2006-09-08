/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.tests;

import conexp.core.LatticeDiagramChecker;
import junit.framework.TestCase;


public class LatticeDiagramCheckerTest extends TestCase {
    private LatticeDiagramChecker checker;

    protected void setUp() {
        checker = new LatticeDiagramChecker();
    }

    public void testIsDiagramOfLatticeForOneNodeCase() {
        checker.setNodeCount(1);
        assertTrue(checker.isDiagramOfLattice());
    }

    public void testIsDiagramOfLatticeForDisjointCase() {
        checker.setNodeCount(2);
        assertEquals("Diagram of lattice should consist from connected nodes", false, checker.isDiagramOfLattice());
    }

    public void testTwoNodeCaseOrderedLattice() {
        checker.setNodeCount(2);
        checker.setLessThan(0, 1); //id of nodes in set less  than doesn't matter
        assertEquals(true, checker.isDiagramOfLattice());
    }

    public void testCyclicOrderRelationCase() {
        checker.setNodeCount(4);
        checker.setLessThan(0, 1);
        checker.setLessThan(1, 2);
        checker.setLessThan(2, 3);
        checker.setLessThan(3, 0);
        assertEquals(false, checker.isDiagramOfLattice());
    }

    public void testRedundantLinksInOrderRelation() {
        checker.setNodeCount(3);
        checker.setLessThan(0, 1);
        checker.setLessThan(1, 2);
        checker.setLessThan(0, 2);
        assertEquals(false, checker.isDiagramOfLattice());
    }

    public void testRedundantLinksInOrderRelationForBiggerLength() {
        checker.setNodeCount(5);
        checker.setLessThan(0, 1);
        checker.setLessThan(1, 2);
        checker.setLessThan(2, 3);
        checker.setLessThan(3, 4);
        checker.setLessThan(0, 4);
        assertEquals(false, checker.isDiagramOfLattice());
    }

    public void testForCaseOfNotSemilattice() {
        checker.setNodeCount(3);
        checker.setLessThan(1, 0);
        checker.setLessThan(2, 0);
        assertEquals(false, checker.isDiagramOfLattice());
        assertEquals(true, checker.isDiagramOfSemilattice());
    }


    public void testTransitiveClosureInLessThanRelationCalculation() {
        checker.setNodeCount(8);
        checker.setLessThan(2, 0);
        checker.setLessThan(3, 2);
        checker.setLessThan(3, 1);
        checker.setLessThan(1, 0);
        checker.setLessThan(7, 1);
        checker.setLessThan(6, 7);
        checker.setLessThan(5, 6);
        checker.setLessThan(5, 3);
        checker.setLessThan(5, 4);
        checker.setLessThan(4, 2);

        assertEquals(true, checker.isDiagramOfLattice());
        assertEquals(true, checker.isDiagramOfSemilattice());

    }

    public void testGetContext() {
        checker.setNodeCount(3);
        checker.setLessThan(1, 0);
        checker.setLessThan(2, 0);
        assertNotNull(checker.getContext());
    }

}
