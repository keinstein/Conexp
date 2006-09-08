/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.core.stability;

import conexp.core.BinaryRelationProcessor;
import conexp.core.Set;



public interface PointStabilityCalculator extends BinaryRelationProcessor {
    double getPointStabilityOfSet(Set attributeSet);
}
