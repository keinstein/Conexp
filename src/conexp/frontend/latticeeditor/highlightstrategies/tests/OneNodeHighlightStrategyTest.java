/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.frontend.latticeeditor.highlightstrategies.tests;

import conexp.frontend.latticeeditor.highlightstrategies.FilterIdealHighlightStrategy;
import conexp.frontend.latticeeditor.highlightstrategies.OneNodeHighlightStrategy;
import conexp.frontend.latticeeditor.tests.HighlightStrategyTest;
import conexp.frontend.latticeeditor.ConceptHighlightStrategy;


public class OneNodeHighlightStrategyTest extends HighlightStrategyTest {
    protected ConceptHighlightStrategy makeNotEqualInstance() {
        return new FilterIdealHighlightStrategy();
    }

    protected ConceptHighlightStrategy makeEqualInstance() {
        return new OneNodeHighlightStrategy();
    }
}
