package conexp.frontend.latticeeditor.highlightstrategies.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Insert the type's description here.
 * Creation date: (01.12.00 4:14:01)
 * @author
 */
public class PackageTest extends junit.framework.TestCase {
    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTest(HighlightStrategyModelTest.suite());
        return suite;
    }
}