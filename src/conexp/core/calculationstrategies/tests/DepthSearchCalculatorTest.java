/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/

package conexp.core.calculationstrategies.tests;

import conexp.core.calculationstrategies.DepthSearchCalculator;
import junit.framework.Test;
import junit.framework.TestSuite;


public class DepthSearchCalculatorTest extends EnumerativeCalcStrategyTest {

    private static final Class THIS = DepthSearchCalculatorTest.class;

    public static Test suite() {
        return new TestSuite(THIS);
    }

    protected conexp.core.ConceptCalcStrategy makeCalcStrategy() {
        return new DepthSearchCalculator();
    }

}
