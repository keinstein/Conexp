/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core;

public interface ConceptLatticeCalcStrategyWithFeatureMask extends ConceptCalcStrategy, LatticeCalcStrategy {
    public void setFeatureMask(Set featureMask);
}
