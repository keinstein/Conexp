package conexp.frontend.latticeeditor.tests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * JUnit test case for LatticePainterOptionsTest
 */

public class LatticePainterOptionsTest extends TestCase {
    private static final Class THIS = LatticePainterOptionsTest.class;

    public static Test suite() {
        return new TestSuite(THIS);
    }


    public void testSmallGridSize() {
        (new conexp.frontend.latticeeditor.LatticePainterOptions()).getSmallGridSize();
    }
}