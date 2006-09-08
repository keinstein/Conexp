/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.frontend.ruleview;

import conexp.core.ImplicationCalcStrategy;
import conexp.core.calculationstrategies.AttributeIncrementalImplicationCalculator;


public class AttributeIncrementalImplicationCalculatorFactory implements ImplicationCalcStrategyFactory {
    public ImplicationCalcStrategy makeImplicationCalcStrategy() {
        return new AttributeIncrementalImplicationCalculator();
    }

    private AttributeIncrementalImplicationCalculatorFactory() {
    }

    private static final ImplicationCalcStrategyFactory gInstance = new AttributeIncrementalImplicationCalculatorFactory();

    public static ImplicationCalcStrategyFactory getInstance() {
        return gInstance;
    }
}
