package conexp.core.compareutils.tests;

import junit.framework.TestCase;
import conexp.core.compareutils.CompareInfo;
import conexp.core.compareutils.DefaultCompareInfoFactory;

/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 */

public class CompareInfoTest extends TestCase {
    public void testSetCorresponding(){
        CompareInfo info = DefaultCompareInfoFactory.getInstance().makeCompareInfo("One", CompareInfo.IN_FIRST);
        assertTrue(info.setCorresponding("One"));
        assertFalse("Corresponding object can be set only once",info.setCorresponding("One"));
    }
}
