/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.latticeeditor.highlightstrategies.tests;

import junit.framework.Test;
import junit.framework.TestSuite;


public class PackageTest extends junit.framework.TestCase {
    public static Test suite() {
        TestSuite suite = new TestSuite();

        suite.addTestSuite(FilterHighlightStrategyTest.class);
        suite.addTestSuite(FilterIdealHighlightStrategyTest.class);
        suite.addTestSuite(IdealHighlightStrategyTest.class);
        suite.addTestSuite(HighlightStrategyModelTest.class);
        suite.addTestSuite(OneNodeHighlightStrategyTest.class);
        suite.addTestSuite(NeigboursHighlightStrategyTest.class);
        suite.addTestSuite(NoHighlightStrategyTest.class);


        return suite;
    }
}
