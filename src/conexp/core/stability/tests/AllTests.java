/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.core.stability.tests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AllTests extends TestCase {

    public static Test suite() {
        TestSuite test = new TestSuite();
        test.addTestSuite(BruteForceStabilityCalculatorTest.class);
        test.addTestSuite(OneConceptPointStabilityCalculatorTest.class);
        test.addTestSuite(PointAndIntegralStabilityCalculatorTest.class);
        return test;
    }
}
