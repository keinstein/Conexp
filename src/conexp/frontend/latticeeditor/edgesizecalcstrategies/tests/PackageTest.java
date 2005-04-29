/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.latticeeditor.edgesizecalcstrategies.tests;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.framework.TestCase;


public class PackageTest extends TestCase {
    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTestSuite(ConceptConnectionEdgeSizeCalcStrategyTest.class);
        suite.addTestSuite(EdgeSizeStrategyModelTest.class);
        suite.addTestSuite(ObjectFlowEdgeSizeCalcStrategyTest.class);
        return suite;
    }
}
