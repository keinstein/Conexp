package conexp.core.stability.tests;

import conexp.core.BinaryRelation;
import conexp.core.tests.SetBuilder;
import conexp.core.stability.PointStabilityCalculator;
import conexp.core.stability.StabilityCalculator;
import conexp.core.stability.tests.PointStabilityCalculatorBaseTest;

public class StabilityCalculatorTest extends PointStabilityCalculatorBaseTest {
    protected PointStabilityCalculator makePointStabilityCalculator() {
        return new conexp.core.stability.StabilityCalculator();
    }

    public void testCalculateStabilityForLatticeCaseWithOneObject(){

        final int[][] arrRelation = new int[][]{
                    {1, 1, 1}
                };

        BinaryRelation relation = SetBuilder.makeRelation(arrRelation);
        conexp.core.stability.StabilityCalculator stabilityCalculator = new StabilityCalculator();
        stabilityCalculator.setRelation(relation);
        assertEquals(1.0, stabilityCalculator.getIntegralStabilityForSet(relation.getSet(0)), PRECISION);
    }

    public void testCalculateStabilityForLatticeCaseWithTwoObjects(){

        int[][] arrRelation = new int[][]{
            {0, 1},
            {1, 0}
        };

        BinaryRelation relation = SetBuilder.makeRelation(arrRelation);
        StabilityCalculator stabilityCalculator = new StabilityCalculator();
        stabilityCalculator.setRelation(relation);
        System.out.println(stabilityCalculator);
        assertEquals(0.25, stabilityCalculator.getIntegralStabilityForSet(SetBuilder.makeSet(new int[]{0, 0})), PRECISION);
        assertEquals(0.5, stabilityCalculator.getIntegralStabilityForSet(SetBuilder.makeSet(new int[]{0, 1})), PRECISION);
        assertEquals(0.5, stabilityCalculator.getIntegralStabilityForSet(SetBuilder.makeSet(new int[]{1, 0})), PRECISION);
        assertEquals(1.0, stabilityCalculator.getIntegralStabilityForSet(SetBuilder.makeSet(new int[]{1, 1})), PRECISION);
    }

}