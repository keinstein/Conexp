/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/

package conexp.core.layout.tests;

import conexp.core.layout.ForceLayout;
import junit.framework.Test;
import junit.framework.TestSuite;


public class ForceLayoutTest extends SimpleForceLayoutTest {
    private static final Class THIS = ForceLayoutTest.class;

    protected conexp.core.layout.GenericLayouter makeLayouter() {
        return new ForceLayout();
    }

    public static Test suite() {
        return new TestSuite(THIS);
    }
}
