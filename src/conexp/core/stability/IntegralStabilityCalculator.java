package conexp.core.stability;

import conexp.core.Set;
import conexp.core.BinaryRelationProcessor;

/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 */

public interface IntegralStabilityCalculator extends BinaryRelationProcessor{
    double getIntegralStabilityForSet(Set set);
}
