package conexp.core.layoutengines.tests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Creation date: (01.12.00 2:31:50)
 */

public class AllTests extends TestCase {
    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTest(SimpleLayoutEngineTest.suite());
        return suite;
    }
}