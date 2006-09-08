/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.layout.tests;

import conexp.core.layout.GenericLayouter;
import conexp.core.layout.RelaxerLayouter;


public class RelaxerLayouterTest extends GenericLayouterTest {
    protected boolean isPureImprovingLayout() {
        return true;
    }

    protected boolean isTestImproveOnce() {
        return true;
    }

    protected GenericLayouter makeLayouter() {
        return new RelaxerLayouter();
    }

}
