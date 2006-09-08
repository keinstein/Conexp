/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core;

public interface ConceptLatticeCalcStrategyWithFeatureMask extends ConceptCalcStrategy, LatticeCalcStrategy {
    void setFeatureMasks(Set attributesMask, Set objectsMask);
}
