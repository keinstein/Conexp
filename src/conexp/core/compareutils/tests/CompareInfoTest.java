/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.core.compareutils.tests;

import conexp.core.compareutils.CompareInfo;
import conexp.core.compareutils.DefaultCompareInfoFactory;
import junit.framework.TestCase;



public class CompareInfoTest extends TestCase {
    public static void testSetCorresponding() {
        CompareInfo info = DefaultCompareInfoFactory.getInstance().makeCompareInfo("Key", "One", CompareInfo.IN_FIRST);
        assertTrue(info.setCorresponding("One"));
        assertFalse("Corresponding object can be set only once", info.setCorresponding("One"));
    }
}
