/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.core.calculationstrategies.tests;

import conexp.core.calculationstrategies.ReferenceDepthSearchCalculator;


public class ReferenceDepthSearchCalculatorTest extends EnumerativeCalcStrategyTest {
    protected conexp.core.ConceptCalcStrategy makeCalcStrategy() {
        return new ReferenceDepthSearchCalculator();
    }

}
