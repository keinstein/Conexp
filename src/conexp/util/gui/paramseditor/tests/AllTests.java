package conexp.util.gui.paramseditor.tests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * JUnit test case for AllTests
 */

public class AllTests extends TestCase {

    public static Test suite() {
        TestSuite suite = new TestSuite();

        suite.addTest(BoundedIntValueParamInfoTest.suite());
        suite.addTest(ParamEditorTableTest.suite());
        suite.addTest(ParamTableModelTest.suite());

        return suite;
    }
}