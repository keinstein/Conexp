/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.core.layout.layeredlayout.tests;

import conexp.core.layout.layeredlayout.LayeredLayoter;
import conexp.core.layout.tests.GenericLayouterTest;

public class LayeredLayouterTest extends GenericLayouterTest {
    protected boolean isTestImproveOnce() {
        return false;
    }

    protected conexp.core.layout.GenericLayouter makeLayouter() {
        return new LayeredLayoter();
    }
}
