package conexp.experimenter.framework.tests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


public class AllTests extends TestCase {
    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTestSuite(MeasurementSetTest.class);
        suite.addTestSuite(MeasurementProtocolTest.class);
        suite.addTestSuite(ExperimentRunnerTest.class);
        return suite;
    }

}