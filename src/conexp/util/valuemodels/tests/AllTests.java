/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.util.valuemodels.tests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AllTests extends TestCase {

    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTestSuite(BooleanValueModelTest.class);
        suite.addTestSuite(BoundedIntValueTest.class);
        suite.addTestSuite(VetoableValueModelDecoratorTest.class);
        return suite;
    }

}
