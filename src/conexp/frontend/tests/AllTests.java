package conexp.frontend.tests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AllTests extends TestCase {

    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTest(ConceptFrameTest.suite());
        suite.addTest(ContextDocManagerTest.suite());
        suite.addTest(PropertyChangeSupplierBaseControllerTest.suite());
        suite.addTest(ContextDocumentTest.suite());
        suite.addTest(JTabPaneViewManagerTest.suite());
        suite.addTest(ViewManagerTest.suite());

        suite.addTest(conexp.frontend.contexteditor.tests.AllTests.suite());
        suite.addTest(conexp.frontend.latticeeditor.tests.AllTests.suite());
        suite.addTest(conexp.frontend.ruleview.tests.AllTests.suite());

        suite.addTest(conexp.frontend.components.tests.AllTests.suite());
        suite.addTest(conexp.frontend.util.tests.AllTests.suite());
        suite.addTest(conexp.frontend.ui.tests.AllTests.suite());
        suite.addTest(conexp.frontend.attributeexploration.tests.AllTests.suite());
        suite.addTest(conexp.frontend.io.tests.AllTests.suite());
        return suite;
    }
}