/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/

package conexp.core.calculationstrategies.tests;

import conexp.core.BinaryRelation;
import conexp.core.ConceptsCollection;
import conexp.core.Lattice;
import conexp.core.ModifiableSet;
import conexp.core.calculationstrategies.DepthSearchCalculatorWithFeatureMask;
import conexp.core.searchconstraints.MinSupportConstrainer;
import conexp.core.tests.SetBuilder;
import junit.framework.Test;
import junit.framework.TestSuite;


public class DepthSearchCalcWithFeatureMaskLatticeBuildingTest extends LatticeBuildingDepthSearchCalculatorTest {
    private static final Class THIS = DepthSearchCalcWithFeatureMaskLatticeBuildingTest.class;

    public static Test suite() {
        return new TestSuite(THIS);
    }

    protected conexp.core.ConceptCalcStrategy makeCalcStrategy() {
        return new DepthSearchCalculatorWithFeatureMask();
    }

    protected void setupStrategy(ConceptsCollection lat) {
        getRealStrategy().setCallback(new conexp.core.enumcallbacks.ConceptSetCallback(lat));
        getRealStrategy().setLattice((Lattice) lat);
    }

    protected DepthSearchCalculatorWithFeatureMask getRealStrategy() {
        return ((DepthSearchCalculatorWithFeatureMask) calcStrategy);
    }

    private void doTestCalcStrategyWithFeatureMask(int[][] context, int[] featureMask, int[][] expOutputIntent, final int[][] expOutputExtents, final int expectedEdgeCount) {
        final ModifiableSet featureMaskSet = SetBuilder.makeSet(featureMask);
        getRealStrategy().setFeatureMask(featureMaskSet);
        doTestCalcStrategyForExpectedIntentsAndExtents(context,
                expOutputIntent,
                expOutputExtents,
                expectedEdgeCount
        );
        doTestCalcStrategyForExpectedSizeForFullLatticeCase(context, expOutputIntent.length);
        assertEquals(featureMaskSet, getLattice().getFeatureMask());
    }


    public void testBuildConceptSetWithFeatureMask() {
        int[][] context = new int[][]{{0, 1},
                                      {1, 0}};

        int[] featureMask = new int[]{1, 0};
        getRealStrategy().setFeatureMask(SetBuilder.makeSet(featureMask));

        int[][] expOutputIntent = new int[][]{{0, 0},
                                              {1, 0}};

        final int[][] expOutputExtents = new int[][]{{1, 1},
                                                     {0, 1}};


        doTestCalcStrategyWithFeatureMask(context, featureMask, expOutputIntent,
                expOutputExtents, 1);
    }

    public void testBuildConceptSetWithFeatureMaskForOneElementLattice() {
        int[][] context = new int[][]{{1, 0}};

        int[] featureMask = new int[]{1, 0};

        int[][] expOutputIntent = new int[][]{{1, 0}};

        final int[][] expOutputExtents = new int[][]{{1}};
        final int expectedEdgeCount = 0;

        doTestCalcStrategyWithFeatureMask(context, featureMask, expOutputIntent, expOutputExtents, expectedEdgeCount);

    }


    public void testBuildLatticeForEmptyFeatureMaskCase() {
        int[][] context = new int[][]{{1, 1, 0},
                                      {1, 0, 1},
                                      {0, 1, 1}};

        int[] featureMask = new int[]{0, 0, 0};

        int[][] expOutputIntent = new int[][]{{0, 0, 0}};

        final int[][] expOutputExtents = new int[][]{{1, 1, 1}};
        final int expectedEdgeCount = 0;

        doTestCalcStrategyWithFeatureMask(context, featureMask, expOutputIntent, expOutputExtents, expectedEdgeCount);

    }


    public void testBuildLatticeForBooleanCase() {
        int[][] context = new int[][]{{1, 1, 0},
                                      {1, 0, 1},
                                      {0, 1, 1}};

        int[] featureMask = new int[]{1, 0, 1};

        int[][] expOutputIntent = new int[][]{{0, 0, 0},
                                              {0, 0, 1},
                                              {1, 0, 0},
                                              {1, 0, 1}};

        final int[][] expOutputExtents = new int[][]{{1, 1, 1},
                                                     {0, 1, 1},
                                                     {1, 1, 0},
                                                     {0, 1, 0}};
        final int expectedEdgeCount = 4;

        doTestCalcStrategyWithFeatureMask(context, featureMask, expOutputIntent, expOutputExtents, expectedEdgeCount);

    }

    public void testBuildIcebergLattice() {
        getRealStrategy().setSearchConstrainter(new MinSupportConstrainer(2));
        int[][] relation = new int[][]{
            {1, 0, 1, 1, 0},
            {0, 1, 1, 0, 1},
            {1, 1, 1, 0, 1},
            {0, 1, 0, 0, 1},
            {1, 1, 1, 0, 1}
        };
        doTestCalcStrategyForExpectedSizeAndEdgeCount(relation, 6, 7);
        getRealStrategy().removeAllSearchConstraints();
    }

    protected void compareExpEdgeCount(BinaryRelation rel, Lattice lat, int expectedEdgeCount) {
        assertEquals("Expected edge count don't match actual edge count", expectedEdgeCount, lat.edgeCount());
    }
}
