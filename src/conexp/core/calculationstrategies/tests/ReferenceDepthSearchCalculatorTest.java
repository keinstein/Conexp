package conexp.core.calculationstrategies.tests;

import conexp.core.calculationstrategies.ReferenceDepthSearchCalculator;
import junit.framework.Test;
import junit.framework.TestSuite;


public class ReferenceDepthSearchCalculatorTest extends EnumerativeCalcStrategyTest {
    private static final Class THIS = ReferenceDepthSearchCalculatorTest.class;

    public static Test suite() {
        return new TestSuite(THIS);
    }

    protected conexp.core.ConceptCalcStrategy makeCalcStrategy() {
        return new ReferenceDepthSearchCalculator();
    }

}