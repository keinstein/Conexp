/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.tests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AllTests extends TestCase {

    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTestSuite(AboutConExpDialogTest.class);
        suite.addTestSuite(ConceptFrameTest.class);
        suite.addTest(ContextDocManagerTest.suite());
        suite.addTest(PropertyChangeSupplierBaseControllerTest.suite());
        suite.addTestSuite(ContextDocumentTest.class);
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
