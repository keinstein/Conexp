/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.calculationstrategies.tests;

import conexp.core.searchconstraints.MinSupportConstrainer;
import conexp.core.searchconstraints.NoSearchConstraint;


public class FrequentSetMinerTest extends DepthSearchCalcWithFeatureMaskLatticeBuildingTest {
    public void testWithNoSearchConstraint() {
        getRealStrategy().setSearchConstrainter(new NoSearchConstraint());
        doTestCalcStrategyForExpectedIntentsAndExtents(new int[][]{
                {0, 1, 1},
                {1, 0, 1},
                {1, 1, 0}
        },
                new int[0][0],
                new int[0][0],
                0);
    }

    public void testWithMinSupportSearchConstraint() {
        getRealStrategy().setSearchConstrainter(new MinSupportConstrainer(3));
        doTestCalcStrategyForExpectedIntentsAndExtents(new int[][]{
                {0, 1, 1},
                {1, 0, 1},
                {1, 1, 0}
        },
                new int[][]{{
                        0, 0, 0
                }},

                new int[][]{{
                        1, 1, 1
                }},
                0);
    }

    public void testWithMinSupportSearchConstraintSecVariant() {
        getRealStrategy().setSearchConstrainter(new MinSupportConstrainer(2));
        doTestCalcStrategyForExpectedIntentsAndExtents(new int[][]{
                {0, 1, 1},
                {1, 0, 1},
                {1, 1, 0}
        },
                new int[][]{
                        {0, 0, 0},
                        {1, 0, 0},
                        {0, 1, 0},
                        {0, 0, 1}
                },

                new int[][]{
                        {1, 1, 1},
                        {0, 1, 1},
                        {1, 0, 1},
                        {1, 1, 0}
                },
                3);
    }

    public void testWithMinSupportSearchConstraintLinearChain() {
        getRealStrategy().setSearchConstrainter(new MinSupportConstrainer(2));
        doTestCalcStrategyForExpectedIntentsAndExtents(new int[][]{
                {1, 0, 0},
                {1, 1, 0},
                {1, 1, 1}
        },
                new int[][]{
                        {1, 0, 0},
                        {1, 1, 0}
                },

                new int[][]{
                        {1, 1, 1},
                        {0, 1, 1}
                },
                1);
    }

    public void testWhenZeroElementExtentIsEmpty() {
        getRealStrategy().setSearchConstrainter(new MinSupportConstrainer(1));
        doTestCalcStrategyForExpectedIntentsAndExtents(new int[][]{
                {1, 0, 0},
                {0, 1, 0},
                {0, 0, 1}
        },
                new int[][]{
                        {0, 0, 0},
                        {1, 0, 0},
                        {0, 1, 0},
                        {0, 0, 1}
                },

                new int[][]{
                        {1, 1, 1},
                        {1, 0, 0},
                        {0, 1, 0},
                        {0, 0, 1}
                },
                3);

    }

}
