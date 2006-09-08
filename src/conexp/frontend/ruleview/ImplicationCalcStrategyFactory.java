/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.frontend.ruleview;

import conexp.core.ImplicationCalcStrategy;



public interface ImplicationCalcStrategyFactory {
    ImplicationCalcStrategy makeImplicationCalcStrategy();
}
