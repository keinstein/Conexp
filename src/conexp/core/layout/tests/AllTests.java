/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.layout.tests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


public class AllTests extends TestCase {

    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTestSuite(MinIntersectionLayoutTest.class);
        suite.addTestSuite(RelaxerLayouterTest.class);
        suite.addTestSuite(ForceLayoutTest.class);
        suite.addTestSuite(ForceDistributionTest.class);
        suite.addTestSuite(FreezeLayoutTest.class);

        suite.addTestSuite(HeightInLatticeLayerAssignmentFuctionTest.class);

        suite.addTest(conexp.core.layout.chaindecomposition.tests.AllTests.suite());
        suite.addTest(conexp.core.layout.layeredlayout.tests.AllTests.suite());
        return suite;
    }

}
