package conexp.util.tests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class UtilsAllTests extends TestCase {

    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTest(conexp.util.gui.tests.GuiAllTests.suite());
        suite.addTest(conexp.util.valuemodels.tests.AllTests.suite());
        return suite;
    }

}