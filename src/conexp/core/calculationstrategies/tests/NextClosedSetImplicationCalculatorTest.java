package conexp.core.calculationstrategies.tests;

import conexp.core.Context;
import conexp.core.ImplicationCalcStrategy;
import conexp.core.calculationstrategies.NextClosedSetCalculator;
import junit.framework.Test;
import junit.framework.TestSuite;

public class NextClosedSetImplicationCalculatorTest extends ImplicationCalculatorTest {
    private static final Class THIS = NextClosedSetImplicationCalculatorTest.class;

    public static Test suite() {
        return new TestSuite(THIS);
    }


    protected ImplicationCalcStrategy makePreparedCalculator(Context cxt) {
        ImplicationCalcStrategy calc = new NextClosedSetCalculator();
        calc.setRelation(cxt.getRelation());
        return calc;
    }

}