package conexp.core.enumerators.tests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * JUnit test case for AllTests
 */

public class AllTests extends TestCase {
    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTest(EdgeMinSupportSelectorTest.suite());
        suite.addTest(FilterEnumeratorTest.suite());
        suite.addTest(IdealEnumeratorTest.suite());

        return suite;
    }


}