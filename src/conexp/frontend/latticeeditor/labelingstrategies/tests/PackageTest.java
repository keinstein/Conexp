package conexp.frontend.latticeeditor.labelingstrategies.tests;

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
        suite.addTest(AllAttribsLabelingStrategyTest.suite());
        suite.addTest(AllObjectsLabelingStrategyTest.suite());

        suite.addTest(AttributesLabelingStrategyModelTest.suite());
        suite.addTest(ObjectsLabelingStrategyModelTest.suite());
        suite.addTest(OwnObjectsCountLabelingStrategyTest.suite());
        suite.addTest(ObjectsCountLabelingStrategyTest.suite());

        return suite;
    }
}