package conexp.core.layout.tests;

import conexp.core.layout.MinIntersectionLayout;
import junit.framework.Test;
import junit.framework.TestSuite;


public class MinIntersectionLayoutTest extends GenericLayouterTest {
    private static final Class THIS = MinIntersectionLayoutTest.class;

    protected boolean isTestImproveOnce() {
        return false;
    }

    protected conexp.core.layout.GenericLayouter makeLayouter() {
        return new MinIntersectionLayout();
    }

    public static Test suite() {
        return new TestSuite(THIS);
    }
}