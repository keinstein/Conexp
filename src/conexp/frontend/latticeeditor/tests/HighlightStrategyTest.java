package conexp.frontend.latticeeditor.tests;

/**
 * Created by IntelliJ IDEA.
 * User: sergey
 * Date: 18/4/2005
 * Time: 1:10:04
 * To change this template use File | Settings | File Templates.
 */

import junit.framework.*;
import conexp.frontend.latticeeditor.HighlightStrategy;
import util.testing.TestUtil;

public abstract class HighlightStrategyTest extends TestCase {
    public void testEqualsAndHashCode() {
        HighlightStrategy first = makeEqualInstance();
        HighlightStrategy second = makeEqualInstance();
        HighlightStrategy third = makeNotEqualInstance();

        TestUtil.testEqualsAndHashCode(first, second);
        TestUtil.testNotEquals(first, third);
    }

    protected abstract HighlightStrategy makeNotEqualInstance();

    protected abstract HighlightStrategy makeEqualInstance();
}