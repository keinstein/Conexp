/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.core.layout.chaindecomposition.tests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


public class AllTests extends TestCase {
    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTest(ChainDecompositionLayoutTest.suite());
        suite.addTest(ChainDecompositionStrategyModelTest.suite());
        suite.addTest(ConceptPlacementStrategyModelTest.suite());
        return suite;
    }

}
