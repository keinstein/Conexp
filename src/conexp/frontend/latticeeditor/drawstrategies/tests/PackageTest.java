package conexp.frontend.latticeeditor.drawstrategies.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Insert the type's description here.
 * Creation date: (12.10.00 23:33:15)
 * @author
 */
public class PackageTest extends junit.framework.TestCase {
    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTest(DefaultDrawStrategiesModelsFactoryTest.suite());
        suite.addTest(DefaultLabelingStrategiesModelsFactoryTest.suite());
        return suite;
    }
}