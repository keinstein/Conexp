/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.associations.tests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


public class AllTests extends TestCase {

    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTest(FrequentSetSupportSupplierTest.suite());
        suite.addTest(SimpleAssociationMinerTest.suite());
        suite.addTest(SecondAssociationMinerTest.suite());

        suite.addTest(AssociationCoverCalculatorTest.suite());
        suite.addTest(AssociationRuleTest.suite());
        return suite;
    }
}
