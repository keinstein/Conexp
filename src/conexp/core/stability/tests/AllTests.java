package conexp.core.stability.tests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AllTests extends TestCase {

    public static Test suite() {
        TestSuite test = new TestSuite();
        test.addTestSuite(BruteForceStabilityCalculatorTest.class);
        test.addTestSuite(OneConceptPointStabilityCalculatorTest.class);
        return test;
    }
}