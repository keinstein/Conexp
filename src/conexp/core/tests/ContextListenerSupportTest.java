/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.core.tests;

import conexp.core.ContextListenerSupport;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


public class ContextListenerSupportTest extends TestCase {
    public void testMarkStructureChange() {
        ContextListenerSupport contextListenerSupport = new ContextListenerSupport(null);
        assertEquals(false, contextListenerSupport.hasStructureChangePostponed());
        contextListenerSupport.madePostponedStructureChange();
        assertEquals(true, contextListenerSupport.hasStructureChangePostponed());
        contextListenerSupport.fireContextStructureChanged();
        assertEquals(false, contextListenerSupport.hasStructureChangePostponed());

    }
}
