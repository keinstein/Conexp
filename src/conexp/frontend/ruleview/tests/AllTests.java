package conexp.frontend.ruleview.tests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * JUnit test case for AllTests
 */

public class AllTests extends TestCase {

    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTest(AssociationRulesViewTest.suite());
        suite.addTest(AssociationRendererTest.suite());
        suite.addTest(ImplicationsViewTest.suite());
        suite.addTest(ImplicationRendererTest.suite());
        return suite;
    }
}