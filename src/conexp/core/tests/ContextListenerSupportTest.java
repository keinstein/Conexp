package conexp.core.tests;

import conexp.core.ContextListenerSupport;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * JUnit test case for ContextTest
 */

public class ContextListenerSupportTest extends TestCase {
    private static final Class THIS = ContextListenerSupportTest.class;

    public static Test suite() {
        return new TestSuite(THIS);
    }

    public void testMarkStructureChange() {
        ContextListenerSupport contextListenerSupport = new ContextListenerSupport(null);
        assertEquals(false, contextListenerSupport.hasStructureChangePostponed());
        contextListenerSupport.madePostponedStructureChange();
        assertEquals(true, contextListenerSupport.hasStructureChangePostponed());
        contextListenerSupport.fireContextStructureChanged();
        assertEquals(false, contextListenerSupport.hasStructureChangePostponed());

    }
}