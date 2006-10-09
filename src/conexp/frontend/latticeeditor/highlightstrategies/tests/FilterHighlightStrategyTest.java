/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.frontend.latticeeditor.highlightstrategies.tests;

import conexp.frontend.latticeeditor.highlightstrategies.FilterHighlightStrategy;
import conexp.frontend.latticeeditor.highlightstrategies.IdealHighlightStrategy;
import conexp.frontend.latticeeditor.tests.HighlightStrategyTest;
import conexp.frontend.latticeeditor.ConceptHighlightStrategy;


public class FilterHighlightStrategyTest extends HighlightStrategyTest {
    protected ConceptHighlightStrategy makeNotEqualInstance() {
        return new IdealHighlightStrategy();
    }

    protected ConceptHighlightStrategy makeEqualInstance() {
        return new FilterHighlightStrategy();
    }
}
