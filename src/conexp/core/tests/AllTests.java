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

        suite.addTestSuite(BinaryRelationUtilsTest.class);
        suite.addTestSuite(ContextTest.class);
        suite.addTestSuite(ContextEntityTest.class);
        suite.addTestSuite(ContextListenerSupportTest.class);
        suite.addTestSuite(ContextFunctionsTest.class);
        suite.addTestSuite(SetRelationTest.class);

        suite.addTestSuite(EdgeTest.class);
        suite.addTestSuite(LatticeElementTest.class);
        suite.addTestSuite(LatticeTest.class);
        suite.addTestSuite(LatticeDiagramCheckerTest.class);

        suite.addTestSuite(ImplicationSetTest.class);
        suite.addTestSuite(ImplicationTest.class);
        suite.addTestSuite(DependencySetTest.class);

        suite.addTestSuite(conexp.core.attrexplorationimpl.tests.AttributeExplorerTest.class);

        suite.addTest(conexp.core.associations.tests.AllTests.suite());
        suite.addTest(conexp.core.bitset.tests.AllTests.suite());
        suite.addTest(conexp.core.calculationstrategies.tests.AllTests.suite());
        suite.addTest(conexp.core.compareutils.tests.AllTests.suite());
        suite.addTest(conexp.core.enumerators.tests.AllTests.suite());
        suite.addTest(conexp.core.layout.tests.AllTests.suite());
        suite.addTest(conexp.core.layoutengines.tests.AllTests.suite());
        suite.addTest(conexp.core.stability.tests.AllTests.suite());
        suite.addTest(conexp.core.utils.tests.AllTests.suite());

        return suite;
    }
}
