package conexp.experimenter.setdecorator.tests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import conexp.experimenter.relationsequences.tests.PercentFilledRelationGeneratorTest;
import conexp.experimenter.relationsequences.tests.ContextLoadingRelationGenerationStrategyTest;

/**
 * JUnit test case for AllTests
 */

public class AllTests extends TestCase {
    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTestSuite(OperationStatisticTest.class);
        return suite;
    }
}