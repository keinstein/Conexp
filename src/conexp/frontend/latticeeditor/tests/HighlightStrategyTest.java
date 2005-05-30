package conexp.frontend.latticeeditor.tests;

/**
 * Created by IntelliJ IDEA.
 * User: sergey
 * Date: 18/4/2005
 * Time: 1:10:04
 * To change this template use File | Settings | File Templates.
 */

import junit.framework.TestCase;
import util.testing.TestUtil;
import canvas.IHighlightStrategy;

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