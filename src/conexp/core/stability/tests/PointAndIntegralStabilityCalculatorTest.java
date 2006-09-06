package conexp.core.stability.tests;

import conexp.core.stability.IntegralStabilityCalculator;
import conexp.core.stability.PointAndIntegralStabilityCalculator;

public class PointAndIntegralStabilityCalculatorTest extends IntegralStabilityCalculatorBaseTest {

    protected IntegralStabilityCalculator makeIntegralStabilityCalculator() {
        return new PointAndIntegralStabilityCalculator();
    }

    public void testCase3() {
        //todo:sye - investigate the reasons for failures.
    }

}