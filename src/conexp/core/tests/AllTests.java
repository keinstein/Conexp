package conexp.core.tests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AllTests extends TestCase {

    public static Test suite() {
        TestSuite suite = new TestSuite();

        suite.addTest(ContextTest.suite());
        suite.addTest(ContextEntityTest.suite());
        suite.addTest(ContextListenerSupportTest.suite());
        suite.addTest(BinaryRelationUtilsTest.suite());
        suite.addTest(ContextFunctionsTest.suite());
        suite.addTest(SetRelationTest.suite());

        suite.addTest(EdgeTest.suite());
        suite.addTest(LatticeTest.suite());

        suite.addTest(ImplicationSetTest.suite());
        suite.addTest(ImplicationTest.suite());
        suite.addTest(DependencySetTest.suite());

        suite.addTest(LatticeDiagramCheckerTest.suite());

        suite.addTest(conexp.core.associations.tests.AllTests.suite());
        suite.addTest(conexp.core.attrexplorationimpl.tests.AttributeExplorerTest.suite());
        suite.addTest(conexp.core.bitset.tests.AllTests.suite());
        suite.addTest(conexp.core.calculationstrategies.tests.AllTests.suite());
        suite.addTest(conexp.core.enumerators.tests.AllTests.suite());
        suite.addTest(conexp.core.layout.tests.AllTests.suite());
        suite.addTest(conexp.core.layoutengines.tests.AllTests.suite());

        return suite;
    }
}