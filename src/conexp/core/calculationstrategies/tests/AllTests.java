/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
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
        suite.addTest(DepthSearchCalcLatticeBuildingTest.suite());
        suite.addTest(DepthSearchCalculatorTest.suite());
        suite.addTest(DepthSearchArrowCalculatorTest.suite());
        suite.addTestSuite(DepthSearchCalcWithFeatureMaskLatticeBuildingTest.class);
        suite.addTestSuite(DepthSearchCalculatorWithFeatureMaskTest.class);
        suite.addTest(NextClosedSetCalcBuildLatTest.suite());
        suite.addTest(ReferenceDepthSearchCalculatorTest.suite());
        suite.addTestSuite(NextClosedSetCalculatorTest.class);
        suite.addTest(FrequentSetMinerTest.suite());

        suite.addTest(LatticeImplicationCalculatorTest.suite());
        suite.addTestSuite(NextClosedSetImplicationCalculatorTest.class);
        return suite;
    }


}
