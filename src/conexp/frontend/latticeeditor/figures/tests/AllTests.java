package conexp.frontend.latticeeditor.figures.tests;

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
        suite.addTest(ConceptFigureTest.suite());
        suite.addTest(NestedDiagramNodeFigureTest.suite());
        suite.addTest(NestedDiagramDecoratingFigureTest.suite());
        return suite;
    }
}