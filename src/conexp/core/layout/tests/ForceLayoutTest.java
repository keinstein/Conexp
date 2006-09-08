/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.layout.tests;

import conexp.core.layout.ForceLayout;
import conexp.core.layout.GenericLayouter;

public class ForceLayoutTest extends SimpleForceLayoutTest {
    protected GenericLayouter makeLayouter() {
        return new ForceLayout();
    }
}
