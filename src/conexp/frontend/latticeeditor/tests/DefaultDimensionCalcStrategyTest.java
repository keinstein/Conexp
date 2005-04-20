package conexp.frontend.latticeeditor.tests;

/**
 * Created by IntelliJ IDEA.
 * User: sergey
 * Date: 17/4/2005
 * Time: 17:01:27
 * To change this template use File | Settings | File Templates.
 */

import junit.framework.*;
import conexp.frontend.latticeeditor.DefaultDimensionCalcStrategy;
import util.testing.TestUtil;

public abstract class DefaultDimensionCalcStrategyTest extends TestCase {

    public void testEqualsAndHashCode() throws Exception {
        final DefaultDimensionCalcStrategy first = makeEqualInstance();
        final DefaultDimensionCalcStrategy second = makeEqualInstance();
        DefaultDimensionCalcStrategy third = makeNotEqualInstance();
        TestUtil.testEqualsAndHashCode(first, second);
        TestUtil.testNotEquals(first, third);
    }

    protected abstract DefaultDimensionCalcStrategy makeNotEqualInstance();

    protected abstract DefaultDimensionCalcStrategy  makeEqualInstance();
}