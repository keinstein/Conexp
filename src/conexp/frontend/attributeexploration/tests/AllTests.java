package conexp.frontend.attributeexploration.tests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * JUnit test case for AllTests
 */

public class AllTests extends TestCase {
    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTest(AttributeExplorerUserCallbackImplementationTest.suite());
        return suite;
    }
}