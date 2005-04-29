package conexp.core.stability.tests;

import conexp.core.stability.PointStabilityCalculator;
import conexp.core.stability.OneConceptPointStabilityCalculator;

public class OneConceptPointStabilityCalculatorTest extends PointStabilityCalculatorBaseTest {

    protected PointStabilityCalculator makePointStabilityCalculator() {
        return new OneConceptPointStabilityCalculator();
    }

}