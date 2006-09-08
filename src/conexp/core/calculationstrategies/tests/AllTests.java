/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.calculationstrategies.tests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AllTests extends TestCase {

    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTestSuite(AttributeIncrementalImplicationCalculatorTest.class);
        suite.addTestSuite(DepthSearchCalcLatticeBuildingTest.class);
        suite.addTestSuite(DepthSearchCalculatorTest.class);

        suite.addTestSuite(DepthSearchArrowCalculatorTest.class);
        suite.addTestSuite(DepthSearchCalcWithFeatureMaskLatticeBuildingTest.class);
        suite.addTestSuite(DepthSearchCalculatorWithFeatureMaskTest.class);
        suite.addTestSuite(NextClosedSetCalcBuildLatTest.class);
        suite.addTestSuite(ReferenceDepthSearchCalculatorTest.class);
        suite.addTestSuite(NextClosedSetCalculatorTest.class);
        suite.addTestSuite(FrequentSetMinerTest.class);

        suite.addTestSuite(LatticeImplicationCalculatorTest.class);
        suite.addTestSuite(NextClosedSetImplicationCalculatorTest.class);
        return suite;
    }


}
