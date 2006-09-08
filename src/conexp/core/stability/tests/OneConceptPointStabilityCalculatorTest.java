/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.core.stability.tests;

import conexp.core.stability.OneConceptPointStabilityCalculator;
import conexp.core.stability.PointStabilityCalculator;

public class OneConceptPointStabilityCalculatorTest extends PointStabilityCalculatorBaseTest {

    protected PointStabilityCalculator makePointStabilityCalculator() {
        return new OneConceptPointStabilityCalculator();
    }

}
