/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.core.tests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AllTests extends TestCase {

    public static Test suite() {
        TestSuite suite = new TestSuite();

        suite.addTestSuite(ContextTest.class);
        suite.addTest(ContextEntityTest.suite());
        suite.addTest(ContextListenerSupportTest.suite());
        suite.addTest(BinaryRelationUtilsTest.suite());
        suite.addTest(ContextFunctionsTest.suite());
        suite.addTest(SetRelationTest.suite());

        suite.addTest(EdgeTest.suite());
        suite.addTest(LatticeTest.suite());

        suite.addTestSuite(ImplicationSetTest.class);
        suite.addTest(ImplicationTest.suite());
        suite.addTest(DependencySetTest.suite());

        suite.addTestSuite(LatticeDiagramCheckerTest.class);

        suite.addTest(conexp.core.associations.tests.AllTests.suite());
        suite.addTest(conexp.core.attrexplorationimpl.tests.AttributeExplorerTest.suite());
        suite.addTest(conexp.core.bitset.tests.AllTests.suite());
        suite.addTest(conexp.core.calculationstrategies.tests.AllTests.suite());
        suite.addTest(conexp.core.enumerators.tests.AllTests.suite());
        suite.addTest(conexp.core.layout.tests.AllTests.suite());
        suite.addTest(conexp.core.layoutengines.tests.AllTests.suite());
        suite.addTest(conexp.core.stability.tests.AllTests.suite());
        suite.addTest(conexp.core.utils.tests.AllTests.suite());

        return suite;
    }
}
