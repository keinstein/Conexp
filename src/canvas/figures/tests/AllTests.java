package canvas.figures.tests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Insert the type's description here.
 * Creation date: (12.10.00 23:33:15)
 * @author Serhiy Yevtushenko
 */
public class AllTests extends TestCase {
    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTest(LineFigureTest.suite());
        suite.addTest(RectangularFigureTest.suite());
        suite.addTest(CompositeFigureTest.suite());
        return suite;
    }
}