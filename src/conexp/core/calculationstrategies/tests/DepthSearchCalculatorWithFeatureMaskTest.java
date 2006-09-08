/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.core.calculationstrategies.tests;

import conexp.core.ConceptCalcStrategy;
import conexp.core.ContextFactoryRegistry;
import conexp.core.ModifiableSet;
import conexp.core.calculationstrategies.DepthSearchCalculatorWithFeatureMask;
import conexp.core.searchconstraints.MinSupportConstrainer;
import conexp.core.tests.SetBuilder;


public class DepthSearchCalculatorWithFeatureMaskTest extends EnumerativeCalcStrategyTest {

    protected ConceptCalcStrategy makeCalcStrategy() {
        return new DepthSearchCalculatorWithFeatureMask();
    }


    private DepthSearchCalculatorWithFeatureMask getRealStrategy() {
        return (DepthSearchCalculatorWithFeatureMask) calcStrategy;
    }

    private void doTestCalcStrategyWithFeatureMask(int[][] context, int[] featureMask, int[][] expOutputIntent, final int[][] expOutputExtents, final int expectedEdgeCount) {
        final ModifiableSet attributesMask = SetBuilder.makeSet(featureMask);
        final ModifiableSet objectsMask = ContextFactoryRegistry.createSet(context.length);
        objectsMask.fill();
        getRealStrategy().setFeatureMasks(attributesMask, objectsMask);
        doTestCalcStrategyForExpectedIntentsAndExtents(context,
                expOutputIntent,
                expOutputExtents,
                expectedEdgeCount);
        doTestCalcStrategyForExpectedSizeForFullLatticeCase(context, expOutputIntent.length);
    }


    public void testBuildConceptSetWithFeatureMask() {
        int[][] context = new int[][]{{0, 1},
                {1, 0}};

        int[] attributeMask = new int[]{1, 0};
        int[] objectsMask = new int[]{1, 1};


        getRealStrategy().setFeatureMasks(SetBuilder.makeSet(attributeMask), SetBuilder.makeSet(objectsMask));

        int[][] expOutputIntent = new int[][]{{0, 0},
                {1, 0}};

        final int[][] expOutputExtents = new int[][]{{1, 1},
                {0, 1}};


        doTestCalcStrategyWithFeatureMask(context, attributeMask, expOutputIntent,
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

        int[][] relation = new int[][]{
                {1, 0, 1, 1, 0},
                {0, 1, 1, 0, 1},
                {1, 1, 1, 0, 1},
                {0, 1, 0, 0, 1},
                {1, 1, 1, 0, 1}
        };

        getRealStrategy().setSearchConstrainter(new MinSupportConstrainer(6));
        doTestCalcStrategyForExpectedSizeForFullLatticeCase(relation, 0);
        getRealStrategy().removeAllSearchConstraints();


        getRealStrategy().setSearchConstrainter(new MinSupportConstrainer(5));
        doTestCalcStrategyForExpectedSizeForFullLatticeCase(relation, 1);
        getRealStrategy().removeAllSearchConstraints();

        getRealStrategy().setSearchConstrainter(new MinSupportConstrainer(2));
        doTestCalcStrategyForExpectedSizeForFullLatticeCase(relation, 6);
        getRealStrategy().removeAllSearchConstraints();

        getRealStrategy().setSearchConstrainter(new MinSupportConstrainer(4));
        doTestCalcStrategyForExpectedSizeForFullLatticeCase(relation, 3);
        getRealStrategy().removeAllSearchConstraints();
    }


}
