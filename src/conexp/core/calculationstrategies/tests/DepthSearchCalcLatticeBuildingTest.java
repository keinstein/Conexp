/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.calculationstrategies.tests;

import conexp.core.ConceptCalcStrategy;
import conexp.core.ConceptsCollection;
import conexp.core.Lattice;
import conexp.core.calculationstrategies.DepthSearchCalculator;
import conexp.core.enumcallbacks.ConceptSetCallback;


public class DepthSearchCalcLatticeBuildingTest extends LatticeBuildingDepthSearchCalculatorTest {
    protected ConceptCalcStrategy makeCalcStrategy() {
        return new DepthSearchCalculator();
    }

    protected void setupStrategy(ConceptsCollection lat) {
        calcStrategy.setCallback(new ConceptSetCallback(lat));
        ((DepthSearchCalculator) calcStrategy).setLattice((Lattice) lat);
    }

}
