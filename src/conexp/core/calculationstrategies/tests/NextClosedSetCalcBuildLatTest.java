/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.core.calculationstrategies.tests;

import conexp.core.ConceptsCollection;
import conexp.core.Lattice;
import conexp.core.calculationstrategies.NextClosedSetCalculator;
import conexp.core.enumcallbacks.NextClosedSetLatticeBuilderCallback;
import junit.framework.Test;
import junit.framework.TestSuite;


public class NextClosedSetCalcBuildLatTest extends LatticeBuildingDepthSearchCalculatorTest {
    private static final Class THIS = NextClosedSetCalcBuildLatTest.class;

    public static Test suite() {
        return new TestSuite(THIS);
    }

    protected conexp.core.ConceptCalcStrategy makeCalcStrategy() {
        return new NextClosedSetCalculator();
    }


    protected void setupStrategy(ConceptsCollection lat) {
        calcStrategy.setCallback(new NextClosedSetLatticeBuilderCallback((Lattice) lat));
    }

}
