package conexp.core.calculationstrategies.tests;

import conexp.core.searchconstraints.MinSupportConstrainer;
import conexp.core.searchconstraints.NoSearchConstraint;
import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Creation date: (12.07.01 16:09:29)
 * @author sergey
 */
public class FrequentSetMinerTest extends DepthSearchCalcWithFeatureMaskLatticeBuildingTest {
    private static final Class THIS = FrequentSetMinerTest.class;

    public static Test suite() {
        return new TestSuite(THIS);
    }

    public void testWithNoSearchConstraint() {
        getRealStrategy().setSearchConstrainter(new NoSearchConstraint());
        doTestCalcStrategyForExpectedIntentsAndExtents(new int[][]{
            {0, 1, 1},
            {1, 0, 1},
            {1, 1, 0}
        },
        new int[0][0],
        new int[0][0],
        0
        );
    }

    public void testWithMinSupportSearchConstraint(){
        getRealStrategy().setSearchConstrainter(new MinSupportConstrainer(3));
        doTestCalcStrategyForExpectedIntentsAndExtents(new int[][]{
            {0, 1, 1},
            {1, 0, 1},
            {1, 1, 0}
        },
        new int[][]{{
           0,0,0
        }},

        new int[][]{{
            1, 1, 1
        }},
                0
        );
    }

    public void testWithMinSupportSearchConstraintSecVariant(){
        getRealStrategy().setSearchConstrainter(new MinSupportConstrainer(2));
        doTestCalcStrategyForExpectedIntentsAndExtents(new int[][]{
            {0, 1, 1},
            {1, 0, 1},
            {1, 1, 0}
        },
        new int[][]{
          {0,0,0},
          {1,0,0},
          {0,1,0},
          {0,0,1}
        },

        new int[][]{
            {1, 1, 1},
            {0, 1, 1},
            {1, 0, 1},
            {1, 1, 0}
        },
                3
        );
    }

    public void testWithMinSupportSearchConstraintLinearChain(){
        getRealStrategy().setSearchConstrainter(new MinSupportConstrainer(2));
        doTestCalcStrategyForExpectedIntentsAndExtents(new int[][]{
            {1, 0, 0},
            {1, 1, 0},
            {1, 1, 1}
        },
        new int[][]{
          {1,0,0},
          {1,1,0}
        },

        new int[][]{
            {1, 1, 1},
            {0, 1, 1}
        },
               1
        );
    }

    public void testWhenZeroElementExtentIsEmpty(){
        getRealStrategy().setSearchConstrainter(new MinSupportConstrainer(1));
        doTestCalcStrategyForExpectedIntentsAndExtents(new int[][]{
            {1, 0, 0},
            {0, 1, 0},
            {0, 0, 1}
        },
        new int[][]{
          {0,0,0},
          {1,0,0},
          {0,1,0},
          {0,0,1}
        },

        new int[][]{
            {1, 1, 1},
            {1, 0, 0},
            {0, 1, 0},
            {0, 0, 1}
        },
                3
        );

    }

}