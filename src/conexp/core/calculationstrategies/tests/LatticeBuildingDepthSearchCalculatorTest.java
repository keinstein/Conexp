/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.calculationstrategies.tests;

import conexp.core.BinaryRelation;
import conexp.core.ConceptsCollection;
import conexp.core.Lattice;
import conexp.core.LatticeCalcStrategy;
import conexp.core.compareutils.LatticeComparator;
import conexp.core.tests.SetBuilder;

public abstract class LatticeBuildingDepthSearchCalculatorTest extends CalcStrategyTest {
    public static final int[][] LIVING_BEING_AND_WATER_RELATION = new int[][]{
            {1, 1, 0, 0, 0, 0, 1, 0, 0},
            {1, 1, 0, 0, 0, 0, 1, 1, 0},
            {1, 1, 1, 0, 0, 0, 1, 1, 0},
            {1, 0, 1, 0, 0, 0, 1, 1, 1},
            {1, 1, 0, 1, 0, 1, 0, 0, 0},
            {1, 1, 1, 1, 0, 1, 0, 0, 0},
            {1, 0, 1, 1, 1, 0, 0, 0, 0},
            {1, 0, 1, 1, 0, 1, 0, 0, 0},
    };
    public static final int[][] TWO_NODE_ONE_ATTR_CONTEXT = new int[][]{
            {0}
    };
    public static final int[][] ONE_NODE_ONE_ATTR_CONTEXT = new int[][]{
            {1}
    };
    public static final int[][] LINEAR_3_NODE_4_ATTR = new int[][]{
            {0, 0, 0, 1},
            {0, 0, 1, 1},
    };
    public static final int[][] NOMINAL_2_NODE_3_ATTR = new int[][]{
            {0, 0, 1},
            {0, 1, 0}
    };
    public static final int[][] NOMINAL_2_NODE_4_ATTR = new int[][]{
            {1, 0, 0, 0},
            {0, 1, 0, 0},
    };

    protected Lattice getLattice() {
        return (Lattice) conceptSet;
    }


    protected void checkEdgeCount(BinaryRelation rel, int expectedEdgeCount) {
        compareExpEdgeCount(rel, getLattice(), expectedEdgeCount);
    }

    protected void compareExpEdgeCount(BinaryRelation rel, Lattice lat, int expectedEdgeCount) {
        LatticeComparator comp = new LatticeComparator(SetBuilder.makeLattice(rel), lat);
        if (!comp.isEqual() || expectedEdgeCount != lat.edgeCount()) {
            comp.dumpDifferencesToSout();
            assertEquals(expectedEdgeCount, lat.edgeCount());
            assertTrue("compare of lattice failed ", false);
        }
    }

    protected void doTestCalcStrategyForExpectedSizeAndEdgeCount(int[][] input, int expectedSize, int expEdgeCount) {
        BinaryRelation rel = SetBuilder.makeRelation(input);
        calcStrategy.setRelation(rel);
        Lattice lattice = makeLattice();
        setupStrategy(lattice);
        getLatticeCalcStrategy().buildLattice();
        assertEquals(expectedSize, lattice.conceptsCount());
        assertEquals(expEdgeCount, lattice.edgeCount());
    }

    protected void generateIntents() {
        getLatticeCalcStrategy().buildLattice();
    }

    private LatticeCalcStrategy getLatticeCalcStrategy() {
        return (LatticeCalcStrategy) calcStrategy;
    }


    /**
     * Insert the method's description here.
     * Creation date: (22.07.01 8:18:57)
     */
    protected ConceptsCollection makeConceptCollection() {
        return makeLattice();
    }

    private static Lattice makeLattice() {
        return new Lattice();
    }


    /**
     * Insert the method's description here.
     * Creation date: (12.07.01 16:08:42)
     *
     * @param lat conexp.core.Lattice
     */
    protected abstract void setupStrategy(ConceptsCollection lat);

    public void testTwoNodeCase() {
        doTestCalcStrategyForExpectedIntentsAndExtents(TWO_NODE_ONE_ATTR_CONTEXT,

                new int[][]{
                        {0},
                        {1}
                },
                new int[][]{
                        {1},
                        {0}
                }, 1);
    }

    public void testOneNodeCase() {
        doTestCalcStrategyForExpectedIntentsAndExtents(ONE_NODE_ONE_ATTR_CONTEXT,

                new int[][]{
                        {1}
                },
                new int[][]{
                        {1}
                }, 0);
    }

    public void testLin3() {
        doTestCalcStrategyForExpectedIntentsAndExtents(LINEAR_3_NODE_4_ATTR, new int[][]{
                {0, 0, 0, 1},
                {0, 0, 1, 1},
                {1, 1, 1, 1},
        },
                new int[][]{
                        {1, 1},
                        {0, 1},
                        {0, 0},

                }, 2);
    }

    public void testNominal2() {
        doTestCalcStrategyForExpectedIntentsAndExtents(NOMINAL_2_NODE_3_ATTR,
                new int[][]{
                        {0, 0, 0},
                        {0, 0, 1},
                        {0, 1, 0},
                        {1, 1, 1}
                },
                new int[][]{
                        {1, 1},
                        {1, 0},
                        {0, 1},
                        {0, 0}
                },
                4);
    }

    public void testBadDepthSeachReduced() {
        int[][] relation = new int[][]{
                {0, 1, 1, 1, 0},
                {1, 0, 1, 1, 0},
                {0, 1, 0, 0, 1},
                {1, 0, 0, 1, 0},
                {0, 1, 0, 1, 1},
                {0, 1, 1, 0, 0},
                {1, 0, 0, 0, 1},
                {0, 0, 1, 1, 1},
                {1, 0, 1, 0, 1},
        };
        doTestCalcStrategyForExpectedIntentsAndExtents(relation,
                new int[][]{
                        {0, 0, 0, 0, 0},
                        {1, 1, 1, 1, 1},
                        {0, 0, 0, 0, 1},
                        {0, 0, 0, 1, 1},
                        {0, 0, 1, 1, 1},
                        {0, 1, 0, 1, 1},
                        {0, 0, 1, 0, 1},
                        {1, 0, 1, 0, 1},
                        {0, 1, 0, 0, 1},
                        {1, 0, 0, 0, 1},
                        {0, 0, 0, 1, 0},
                        {0, 0, 1, 1, 0},
                        {0, 1, 1, 1, 0},
                        {1, 0, 1, 1, 0},
                        {0, 1, 0, 1, 0},
                        {1, 0, 0, 1, 0},
                        {0, 0, 1, 0, 0},
                        {0, 1, 1, 0, 0},
                        {1, 0, 1, 0, 0},
                        {0, 1, 0, 0, 0},
                        {1, 0, 0, 0, 0},
                },
                new int[][]{
                        {1, 1, 1, 1, 1, 1, 1, 1, 1},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 1, 0, 1, 0, 1, 1, 1},
                        {0, 0, 0, 0, 1, 0, 0, 1, 0},
                        {0, 0, 0, 0, 0, 0, 0, 1, 0},
                        {0, 0, 0, 0, 1, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 1, 1},
                        {0, 0, 0, 0, 0, 0, 0, 0, 1},
                        {0, 0, 1, 0, 1, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 1, 0, 1},
                        {1, 1, 0, 1, 1, 0, 0, 1, 0},
                        {1, 1, 0, 0, 0, 0, 0, 1, 0},
                        {1, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 1, 0, 0, 0, 0, 0, 0, 0},
                        {1, 0, 0, 0, 1, 0, 0, 0, 0},
                        {0, 1, 0, 1, 0, 0, 0, 0, 0},
                        {1, 1, 0, 0, 0, 1, 0, 1, 1},
                        {1, 0, 0, 0, 0, 1, 0, 0, 0},
                        {0, 1, 0, 0, 0, 0, 0, 0, 1},
                        {1, 0, 1, 0, 1, 1, 0, 0, 0},
                        {0, 1, 0, 1, 0, 0, 1, 0, 1},
                },
                43);

    }

    public void testNextClosedSetHardLatticeSubcase1() {
        doTestCalcStrategyForExpectedIntentsAndExtents(new int[][]{
                {1, 0, 0, 0, 0, 0, 1, 0},
                {0, 1, 0, 1, 0, 1, 1, 0},
                {0, 0, 0, 1, 0, 0, 0, 1},
                {0, 1, 1, 0, 0, 0, 0, 1},
                {0, 0, 0, 0, 1, 0, 0, 1},
        },
                new int[][]{
                        {0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 1},
                        {0, 0, 0, 0, 0, 0, 1, 0},
                        {0, 0, 0, 0, 1, 0, 0, 1},
                        {0, 0, 0, 1, 0, 0, 0, 0},
                        {0, 0, 0, 1, 0, 0, 0, 1},
                        {0, 1, 0, 0, 0, 0, 0, 0},
                        {0, 1, 0, 1, 0, 1, 1, 0},
                        {0, 1, 1, 0, 0, 0, 0, 1},
                        {1, 0, 0, 0, 0, 0, 1, 0},
                        {1, 1, 1, 1, 1, 1, 1, 1},
                },
                new int[][]{
                        {1, 1, 1, 1, 1},
                        {0, 0, 1, 1, 1},
                        {1, 1, 0, 0, 0},
                        {0, 0, 0, 0, 1},
                        {0, 1, 1, 0, 0},
                        {0, 0, 1, 0, 0},
                        {0, 1, 0, 1, 0},
                        {0, 1, 0, 0, 0},
                        {0, 0, 0, 1, 0},
                        {1, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0},
                },
                18);
    }

    public void testNominalTwoObjectCase() {
        doTestCalcStrategyForExpectedIntentsAndExtents(NOMINAL_2_NODE_4_ATTR,
                new int[][]{
                        {0, 0, 0, 0},
                        {1, 0, 0, 0},
                        {0, 1, 0, 0},
                        {1, 1, 1, 1}
                },
                new int[][]{
                        {0, 0},
                        {1, 0},
                        {0, 1},
                        {1, 1}
                },
                4);


    }

    public void testLivingObjectsAndWaterLattice() {

        int[][] expIntents = {
                {1, 0, 0, 0, 0, 0, 0, 0, 0},
                {1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 0, 0, 0, 0, 0, 1, 0, 0},
                {1, 0, 0, 0, 0, 0, 1, 1, 0},
                {1, 0, 1, 0, 0, 0, 1, 1, 0},
                {1, 0, 1, 0, 0, 0, 1, 1, 1},
                {1, 1, 1, 0, 0, 0, 1, 1, 0},
                {1, 1, 0, 0, 0, 0, 1, 1, 0},
                {1, 1, 0, 0, 0, 0, 1, 0, 0},
                {1, 0, 0, 1, 0, 0, 0, 0, 0},
                {1, 0, 0, 1, 0, 1, 0, 0, 0},
                {1, 0, 1, 1, 0, 1, 0, 0, 0},
                {1, 1, 1, 1, 0, 1, 0, 0, 0},
                {1, 1, 0, 1, 0, 1, 0, 0, 0},
                {1, 0, 1, 1, 0, 0, 0, 0, 0},
                {1, 0, 1, 1, 1, 0, 0, 0, 0},
                {1, 0, 1, 0, 0, 0, 0, 0, 0},
                {1, 1, 1, 0, 0, 0, 0, 0, 0},
                {1, 1, 0, 0, 0, 0, 0, 0, 0},
        };
        int[][] expExtents = {
                {1, 1, 1, 1, 1, 1, 1, 1},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {1, 1, 1, 1, 0, 0, 0, 0},
                {0, 1, 1, 1, 0, 0, 0, 0},
                {0, 0, 1, 1, 0, 0, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 0},
                {0, 0, 1, 0, 0, 0, 0, 0},
                {0, 1, 1, 0, 0, 0, 0, 0},
                {1, 1, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 1, 1, 1},
                {0, 0, 0, 0, 1, 1, 0, 1},
                {0, 0, 0, 0, 0, 1, 0, 1},
                {0, 0, 0, 0, 0, 1, 0, 0},
                {0, 0, 0, 0, 1, 1, 0, 0},
                {0, 0, 0, 0, 0, 1, 1, 1},
                {0, 0, 0, 0, 0, 0, 1, 0},
                {0, 0, 1, 1, 0, 1, 1, 1},
                {0, 0, 1, 0, 0, 1, 0, 0},
                {1, 1, 1, 0, 1, 1, 0, 0},
        };

        doTestCalcStrategyForExpectedIntentsAndExtents(LIVING_BEING_AND_WATER_RELATION, expIntents, expExtents, 32);

    }
}
