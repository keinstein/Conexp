/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor.figures.tests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


public class AllTests extends TestCase {
    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTestSuite(ConceptFigureTest.class);
        suite.addTestSuite(NestedDiagramNodeFigureTest.class);
        suite.addTestSuite(NestedDiagramDecoratingFigureTest.class);
        return suite;
    }
}
