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

    protected boolean isTestImproveOnce() {
        return true;
    }

    protected conexp.core.layout.GenericLayouter makeLayouter() {
        return new conexp.core.layout.RelaxerLayouter();
    }

}
