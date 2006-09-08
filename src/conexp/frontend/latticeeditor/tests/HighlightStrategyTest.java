/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.frontend.latticeeditor.tests;



import canvas.IHighlightStrategy;
import junit.framework.TestCase;
import util.testing.TestUtil;

public abstract class HighlightStrategyTest extends TestCase {
    public void testEqualsAndHashCode() {
        IHighlightStrategy first = makeEqualInstance();
        IHighlightStrategy second = makeEqualInstance();
        IHighlightStrategy third = makeNotEqualInstance();

        TestUtil.testEqualsAndHashCode(first, second);
        TestUtil.testNotEquals(first, third);
    }

    protected abstract IHighlightStrategy makeNotEqualInstance();

    protected abstract IHighlightStrategy makeEqualInstance();
}
