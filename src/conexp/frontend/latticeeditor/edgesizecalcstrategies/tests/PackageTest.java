package conexp.frontend.latticeeditor.edgesizecalcstrategies.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Insert the type's description here.
 * Creation date: (01.12.00 2:31:50)
 * @author
 */
public class PackageTest extends junit.framework.TestCase {
    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTest(EdgeSizeStrategyModelTest.suite());
        return suite;
    }
}