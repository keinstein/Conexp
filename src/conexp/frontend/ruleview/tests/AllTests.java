/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.ruleview.tests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


public class AllTests extends TestCase {

    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTestSuite(AssociationRulesViewTest.class);
        suite.addTestSuite(AssociationRendererTest.class);
        suite.addTestSuite(ImplicationsViewTest.class);
        suite.addTestSuite(ImplicationRendererTest.class);
        suite.addTestSuite(RulePaneTest.class);
        return suite;
    }
}
