/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
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
        suite.addTest(MinIntersectionLayoutTest.suite());
        suite.addTest(RelaxerLayouterTest.suite());
        suite.addTest(ForceLayoutTest.suite());
        suite.addTest(ForceDistributionTest.suite());
        suite.addTest(FreezeLayoutTest.suite());

        suite.addTest(conexp.core.layout.chaindecomposition.tests.AllTests.suite());
        return suite;
    }

}
