package conexp.tests;

/**
 * AllTests.java
 *
 *
 * Created: Wed Jul 26 01:03:48 2000
 *
 * @author Sergey Yevtushenko
 * @version
 */

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