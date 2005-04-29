/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package canvas.tests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import canvas.util.tests.AllUtilsTests;

public class AllTests extends TestCase {
    public static Test suite() {
        TestSuite suite = new TestSuite();

        suite.addTest(DefaultCanvasSchemeTest.suite());
        suite.addTest(DefaultToolTest.suite());
        suite.addTest(FigureDrawingCanvasTest.suite());
        suite.addTest(FigureDrawingTest.suite());

        suite.addTest(canvas.figures.tests.AllTests.suite());
        suite.addTest(AllUtilsTests.suite());

        return suite;
    }
}
