package canvas.util.tests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


//this class is not called AllTests due to bug in Ant.
public class AllUtilsTests extends TestCase {

    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTest(ToolToggleButtonTest.suite());
        return suite;
    }
}