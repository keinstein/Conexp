/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.util.gui.paramseditor.tests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


public class AllTests extends TestCase {

    public static Test suite() {
        TestSuite suite = new TestSuite();

        suite.addTest(BoundedIntValueParamInfoTest.suite());
        suite.addTest(ParamEditorTableTest.suite());
        suite.addTest(ParamTableModelTest.suite());

        return suite;
    }
}
