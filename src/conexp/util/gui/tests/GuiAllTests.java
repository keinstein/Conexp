package conexp.util.gui.tests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


public class GuiAllTests extends TestCase {

    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTest(conexp.util.gui.paramseditor.tests.AllTests.suite());
        return suite;
    }

}