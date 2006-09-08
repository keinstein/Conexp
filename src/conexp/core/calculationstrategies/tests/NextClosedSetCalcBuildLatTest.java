/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.calculationstrategies.tests;

import conexp.core.ConceptCalcStrategy;
import conexp.core.ConceptsCollection;
import conexp.core.Lattice;
import conexp.core.calculationstrategies.NextClosedSetCalculator;
import conexp.core.enumcallbacks.NextClosedSetLatticeBuilderCallback;


public class NextClosedSetCalcBuildLatTest extends LatticeBuildingDepthSearchCalculatorTest {
    protected ConceptCalcStrategy makeCalcStrategy() {
        return new NextClosedSetCalculator();
    }


    protected void setupStrategy(ConceptsCollection lat) {
        calcStrategy.setCallback(new NextClosedSetLatticeBuilderCallback((Lattice) lat));
    }

}
