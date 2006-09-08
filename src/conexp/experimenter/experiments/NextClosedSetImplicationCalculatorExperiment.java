/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/

package conexp.experimenter.experiments;

import conexp.core.ImplicationCalcStrategy;
import conexp.core.calculationstrategies.NextClosedSetImplicationCalculator;

public class NextClosedSetImplicationCalculatorExperiment extends ImplicationSetExperiment {
    protected ImplicationCalcStrategy makeImplicationsCalcStrategy() {
        return new NextClosedSetImplicationCalculator();
    }
}
