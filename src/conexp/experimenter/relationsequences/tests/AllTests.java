package conexp.experimenter.relationsequences.tests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * JUnit test case for AllTests
 */

public class AllTests extends TestCase {
    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTestSuite(PercentFilledRelationGeneratorTest.class);
        suite.addTestSuite(ContextLoadingRelationGenerationStrategyTest.class);

        return suite;
    }
}