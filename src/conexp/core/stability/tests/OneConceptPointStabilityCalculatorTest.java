package conexp.core.stability.tests;

import conexp.core.stability.tests.PointStabilityCalculatorBaseTest;
import conexp.core.stability.PointStabilityCalculator;

public class OneConceptPointStabilityCalculatorTest extends PointStabilityCalculatorBaseTest {

    protected PointStabilityCalculator makePointStabilityCalculator() {
        return new conexp.core.stability.OneConceptPointStabilityCalculator();
    }

    public void testCalculateStabilityForCaseOfOnlyOneConcept() {
        super.testCalculateStabilityForCaseOfOnlyOneConcept();
    }

}