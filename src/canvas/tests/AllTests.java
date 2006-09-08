/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package canvas.tests;

import canvas.util.tests.AllUtilsTests;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AllTests extends TestCase {
    public static Test suite() {
        TestSuite suite = new TestSuite();

        suite.addTestSuite(DefaultCanvasSchemeTest.class);
        suite.addTestSuite(DefaultToolTest.class);
        suite.addTestSuite(FigureDrawingCanvasTest.class);
        suite.addTestSuite(FigureDrawingTest.class);

        suite.addTest(canvas.figures.tests.AllTests.suite());
        suite.addTest(AllUtilsTests.suite());

        return suite;
    }
}
