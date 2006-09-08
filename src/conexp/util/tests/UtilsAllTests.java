/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.util.tests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class UtilsAllTests extends TestCase {

    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTest(conexp.util.gui.strategymodel.tests.AllTests.suite());
        suite.addTest(conexp.util.gui.tests.GuiAllTests.suite());
        suite.addTest(conexp.util.valuemodels.tests.AllTests.suite());
        return suite;
    }

}
