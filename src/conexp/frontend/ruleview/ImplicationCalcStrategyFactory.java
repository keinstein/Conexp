package conexp.frontend.ruleview;

import conexp.core.ImplicationCalcStrategy;

/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 */

public interface ImplicationCalcStrategyFactory {
    ImplicationCalcStrategy makeImplicationCalcStrategy();
}
