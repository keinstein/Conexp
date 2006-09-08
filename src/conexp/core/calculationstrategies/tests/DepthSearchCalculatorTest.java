/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.calculationstrategies.tests;

import conexp.core.ConceptCalcStrategy;
import conexp.core.calculationstrategies.DepthSearchCalculator;


public class DepthSearchCalculatorTest extends EnumerativeCalcStrategyTest {

    protected ConceptCalcStrategy makeCalcStrategy() {
        return new DepthSearchCalculator();
    }

}
