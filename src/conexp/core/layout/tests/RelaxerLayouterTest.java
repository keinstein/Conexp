/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.core.layout.tests;

import junit.framework.Test;
import junit.framework.TestSuite;


public class RelaxerLayouterTest extends GenericLayouterTest {
    protected boolean isPureImprovingLayout() {
        return true;
    }

    /**
     * Insert the method's description here.
     * Creation date: (30.03.01 4:47:12)
     * @return boolean
     */
    protected boolean isTestImproveOnce() {
        return true;
    }

    /**
     * Insert the method's description here.
     * Creation date: (30.03.01 4:47:12)
     * @return conexp.core.layout.GenericLayouter
     */
    protected conexp.core.layout.GenericLayouter makeLayouter() {
        return new conexp.core.layout.RelaxerLayouter();
    }

    /**
     * Insert the method's description here.
     * Creation date: (30.03.01 4:52:44)
     * @return junit.framework.Test
     */
    public static Test suite() {
        return new TestSuite(RelaxerLayouterTest.class);
    }
}
