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
        suite.addTest(DepthSearchCalcWithFeatureMaskLatticeBuildingTest.suite());
        suite.addTest(DepthSearchCalculatorWithFeatureMaskTest.suite());
        suite.addTest(NextClosedSetCalcBuildLatTest.suite());
        suite.addTest(ReferenceDepthSearchCalculatorTest.suite());
        suite.addTest(NextClosedSetCalculatorTest.suite());
        suite.addTest(FrequentSetMinerTest.suite());

        suite.addTest(LatticeImplicationCalculatorTest.suite());
        suite.addTest(NextClosedSetImplicationCalculatorTest.suite());
        return suite;
    }



}