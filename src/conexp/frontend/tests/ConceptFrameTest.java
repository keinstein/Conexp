/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.tests;

import conexp.frontend.ConceptFrame;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import util.StringUtil;


public class ConceptFrameTest extends TestCase {
    public void testConceptFrame() {
        try {
            new ConceptFrame();
        } catch (Throwable ex) {
            fail("ConceptFrame shouldn't throw exceptions \n" + StringUtil.stackTraceToString(ex));
        }
    }
}
