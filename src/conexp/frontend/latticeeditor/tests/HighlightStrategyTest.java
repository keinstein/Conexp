/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.frontend.latticeeditor.tests;



import canvas.IHighlightStrategy;
import junit.framework.TestCase;
import util.testing.TestUtil;
import conexp.frontend.latticeeditor.ConceptHighlightStrategy;

public abstract class HighlightStrategyTest extends TestCase {
    public void testEqualsAndHashCode() {
        ConceptHighlightStrategy first = makeEqualInstance();
        ConceptHighlightStrategy second = makeEqualInstance();
        ConceptHighlightStrategy third = makeNotEqualInstance();

        TestUtil.testEqualsAndHashCode(first, second);
        TestUtil.testNotEquals(first, third);
    }

    protected abstract ConceptHighlightStrategy makeNotEqualInstance();

    protected abstract ConceptHighlightStrategy makeEqualInstance();
}
