/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.core.layout.tests;

import conexp.core.layout.ForceLayout;

public class ForceLayoutTest extends SimpleForceLayoutTest {
    protected conexp.core.layout.GenericLayouter makeLayouter() {
        return new ForceLayout();
    }
}
