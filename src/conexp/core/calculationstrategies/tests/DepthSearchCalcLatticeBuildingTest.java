/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.core.calculationstrategies.tests;

import conexp.core.ConceptsCollection;
import conexp.core.Lattice;
import conexp.core.calculationstrategies.DepthSearchCalculator;
import junit.framework.Test;
import junit.framework.TestSuite;


public class DepthSearchCalcLatticeBuildingTest extends LatticeBuildingDepthSearchCalculatorTest {
    protected conexp.core.ConceptCalcStrategy makeCalcStrategy() {
        return new DepthSearchCalculator();
    }

    protected void setupStrategy(ConceptsCollection lat) {
        calcStrategy.setCallback(new conexp.core.enumcallbacks.ConceptSetCallback(lat));
        ((DepthSearchCalculator) calcStrategy).setLattice((Lattice) lat);
    }

}
