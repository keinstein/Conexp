/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.core.calculationstrategies.tests;

import conexp.core.calculationstrategies.ReferenceDepthSearchCalculator;
import conexp.core.ConceptCalcStrategy;


public class ReferenceDepthSearchCalculatorTest extends EnumerativeCalcStrategyTest {
    protected ConceptCalcStrategy makeCalcStrategy() {
        return new ReferenceDepthSearchCalculator();
    }

}
