package conexp.frontend.ruleview;

import conexp.core.ImplicationCalcStrategy;
import conexp.core.calculationstrategies.NextClosedSetImplicationCalculator;

/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 */
public class NextClosedSetImplicationCalculatorFactory implements ImplicationCalcStrategyFactory {
    public ImplicationCalcStrategy makeImplicationCalcStrategy() {
        return new NextClosedSetImplicationCalculator();
    }

    private NextClosedSetImplicationCalculatorFactory() {}

    private static final ImplicationCalcStrategyFactory gInstance = new NextClosedSetImplicationCalculatorFactory();

    public static ImplicationCalcStrategyFactory getInstance(){
            return gInstance;
    }
}
