package conexp.experimenter.tests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * JUnit test case for AllTests
 */

public class AllTests extends TestCase {

    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTest(conexp.experimenter.experiments.tests.AllTests.suite());
        suite.addTest(conexp.experimenter.framework.tests.AllTests.suite());
        suite.addTest(conexp.experimenter.relationsequences.tests.AllTests.suite());

        return suite;
    }


}