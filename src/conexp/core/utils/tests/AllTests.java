/*
 * User: sergey
 * Date: Oct 20, 2001
 * Time: 5:03:19 PM
 */
package conexp.core.utils.tests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AllTests extends TestCase {
    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTestSuite(PowerSetIteratorTest.class);
        return suite;
    }
}
