/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.core.layout.tests;

import conexp.core.layout.MinIntersectionLayout;

public class MinIntersectionLayoutTest extends GenericLayouterTest {
    protected boolean isTestImproveOnce() {
        return false;
    }

    protected conexp.core.layout.GenericLayouter makeLayouter() {
        return new MinIntersectionLayout();
    }
}
