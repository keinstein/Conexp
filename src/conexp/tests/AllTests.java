/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.tests;


import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AllTests extends TestCase {

    public static Test suite() {
        TestSuite test = new TestSuite();
        test.addTest(conexp.core.tests.AllTests.suite());
        test.addTest(conexp.frontend.tests.AllTests.suite());
        test.addTest(conexp.util.tests.UtilsAllTests.suite());
        return test;
    }
}
