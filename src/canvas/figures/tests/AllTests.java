/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package canvas.figures.tests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


public class AllTests extends TestCase {
    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTest(LineFigureTest.suite());
        suite.addTest(RectangularFigureTest.suite());
        suite.addTest(CompositeFigureTest.suite());
        return suite;
    }
}
