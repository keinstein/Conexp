package conexp.frontend.tests;

import conexp.frontend.ConceptFrame;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import util.StringUtil;

/**
 * JUnit test case for TestContextDocManager
 */

public class ConceptFrameTest extends TestCase {
    private static final Class THIS = ConceptFrameTest.class;

    public static Test suite() {
        return new TestSuite(THIS);
    }


    public void testConceptFrame() {
        try {
            new ConceptFrame();
        } catch (Throwable ex) {
            fail("ConceptFrame shouldn't throw exceptions \n" + StringUtil.stackTraceToString(ex));
        }
    }
}