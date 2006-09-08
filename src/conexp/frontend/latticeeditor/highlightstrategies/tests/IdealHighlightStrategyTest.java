/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.frontend.latticeeditor.highlightstrategies.tests;

import canvas.IHighlightStrategy;
import conexp.frontend.latticeeditor.highlightstrategies.FilterIdealHighlightStrategy;
import conexp.frontend.latticeeditor.highlightstrategies.IdealHighlightStrategy;
import conexp.frontend.latticeeditor.tests.HighlightStrategyTest;




public class IdealHighlightStrategyTest extends HighlightStrategyTest {
    protected IHighlightStrategy makeNotEqualInstance() {
        return new FilterIdealHighlightStrategy();
    }

    protected IHighlightStrategy makeEqualInstance() {
        return new IdealHighlightStrategy();
    }
}
