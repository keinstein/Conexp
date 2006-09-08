/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.frontend.ruleview;

import conexp.core.ImplicationCalcStrategy;
import conexp.core.calculationstrategies.NextClosedSetImplicationCalculator;


public class NextClosedSetImplicationCalculatorFactory implements ImplicationCalcStrategyFactory {
    public ImplicationCalcStrategy makeImplicationCalcStrategy() {
        return new NextClosedSetImplicationCalculator();
    }

    private NextClosedSetImplicationCalculatorFactory() {
    }

    private static final ImplicationCalcStrategyFactory gInstance = new NextClosedSetImplicationCalculatorFactory();

    public static ImplicationCalcStrategyFactory getInstance() {
        return gInstance;
    }
}
