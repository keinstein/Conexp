/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.tests;

import conexp.core.ContextListenerSupport;
import junit.framework.TestCase;


public class ContextListenerSupportTest extends TestCase {
    public static void testMarkStructureChange() {
        ContextListenerSupport contextListenerSupport = new ContextListenerSupport(null);
        assertEquals(false, contextListenerSupport.hasStructureChangePostponed());
        contextListenerSupport.madePostponedStructureChange();
        assertEquals(true, contextListenerSupport.hasStructureChangePostponed());
        contextListenerSupport.fireContextStructureChanged();
        assertEquals(false, contextListenerSupport.hasStructureChangePostponed());

    }
}
