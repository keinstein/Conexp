package conexp.core.layout.chaindecomposition.tests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * JUnit test case for AllTests
 */

public class AllTests extends TestCase {
    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTest(ChainDecompositionLayoutTest.suite());
        suite.addTest(ChainDecompositionStrategyModelTest.suite());
        suite.addTest(ConceptPlacementStrategyModelTest.suite());
        return suite;
    }

}