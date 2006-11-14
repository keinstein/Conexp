/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
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

        suite.addTestSuite(BaseLatticePainterPaneTest.class);
        suite.addTestSuite(ConscriptLatticeExporterTest.class);
        suite.addTestSuite(DotExporterTest.class);
        suite.addTestSuite(LatticeCanvasDrawStrategiesContextTest.class);
        suite.addTestSuite(LatticeCanvasTest.class);
        suite.addTestSuite(LatticeDrawingTest.class);
        suite.addTestSuite(LatticePainterDrawParamsTest.class);
        suite.addTestSuite(LatticePainterOptionsTest.class);
        suite.addTestSuite(LatticePainterPanelTest.class);
        suite.addTestSuite(HighlighterTest.class);

        suite.addTestSuite(NestedLineDiagramDrawingTest.class);
        suite.addTestSuite(RescaleByYFigureVisitorTest.class);
        suite.addTestSuite(XmlFileStrategyModelTest.class);

        suite.addTest(conexp.frontend.latticeeditor.drawstrategies.tests.AllTests.suite());
        suite.addTest(conexp.frontend.latticeeditor.edgesizecalcstrategies.tests.AllTests.suite());
        suite.addTest(conexp.frontend.latticeeditor.figures.tests.AllTests.suite());
        suite.addTest(conexp.frontend.latticeeditor.highlightstrategies.tests.AllTests.suite());
        suite.addTest(conexp.frontend.latticeeditor.labelingstrategies.tests.AllTests.suite());
        suite.addTest(conexp.frontend.latticeeditor.movestrategies.tests.AllTests.suite());
        suite.addTest(conexp.frontend.latticeeditor.noderadiusstrategy.tests.AllTests.suite());

//        suite.addTest(conexp.frontend.latticeeditor.queries.tests.AllTests.suite());

        return suite;
    }
}
