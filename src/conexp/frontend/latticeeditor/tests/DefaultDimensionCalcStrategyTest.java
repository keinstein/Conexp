/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.frontend.latticeeditor.tests;



import conexp.frontend.latticeeditor.DefaultDimensionCalcStrategy;
import junit.framework.TestCase;
import util.testing.TestUtil;

public abstract class DefaultDimensionCalcStrategyTest extends TestCase {

    public void testEqualsAndHashCode() {
        final DefaultDimensionCalcStrategy first = makeEqualInstance();
        final DefaultDimensionCalcStrategy second = makeEqualInstance();
        DefaultDimensionCalcStrategy third = makeNotEqualInstance();
        TestUtil.testEqualsAndHashCode(first, second);
        TestUtil.testNotEquals(first, third);
    }

    protected abstract DefaultDimensionCalcStrategy makeNotEqualInstance();

    protected abstract DefaultDimensionCalcStrategy makeEqualInstance();
}
