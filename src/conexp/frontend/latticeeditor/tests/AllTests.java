/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.latticeeditor.tests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AllTests extends TestCase {

    public static Test suite() {
        TestSuite suite = new TestSuite();

        suite.addTest(LatticeDrawingTest.suite());
        suite.addTest(LatticePainterOptionsTest.suite());
        suite.addTest(LatticePainterPanelTest.suite());
        suite.addTest(LatticeCanvasTest.suite());

        suite.addTestSuite(NestedLineDiagramDrawingTest.class);


        suite.addTest(XmlFileStrategyModelTest.suite());

        suite.addTest(conexp.frontend.latticeeditor.drawstrategies.tests.PackageTest.suite());
        suite.addTest(conexp.frontend.latticeeditor.edgesizecalcstrategies.tests.PackageTest.suite());
        suite.addTest(conexp.frontend.latticeeditor.figures.tests.AllTests.suite());
        suite.addTest(conexp.frontend.latticeeditor.highlightstrategies.tests.PackageTest.suite());
        suite.addTest(conexp.frontend.latticeeditor.labelingstrategies.tests.PackageTest.suite());
        suite.addTest(conexp.frontend.latticeeditor.noderadiusstrategy.tests.AllTests.suite());

//        suite.addTest(conexp.frontend.latticeeditor.queries.tests.AllTests.suite());

        return suite;
    }
}
