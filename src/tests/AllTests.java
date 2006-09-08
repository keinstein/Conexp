/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package tests;


import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AllTests extends TestCase {

    public static Test suite() {
        TestSuite test = new TestSuite();
        test.addTest(cefacade.tests.AllTests.suite());
        test.addTest(canvas.tests.AllTests.suite());
        test.addTest(conexp.tests.AllTests.suite());
        return test;
    }
}
