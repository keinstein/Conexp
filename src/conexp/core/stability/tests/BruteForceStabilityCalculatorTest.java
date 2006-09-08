/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.core.stability.tests;

import conexp.core.stability.BruteForceIntegralStabilityCalculator;
import conexp.core.stability.IntegralStabilityCalculator;

public class BruteForceStabilityCalculatorTest extends IntegralStabilityCalculatorBaseTest {

    protected IntegralStabilityCalculator makeIntegralStabilityCalculator() {
        return new BruteForceIntegralStabilityCalculator();
    }

}
