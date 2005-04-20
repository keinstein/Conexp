package conexp.frontend.latticeeditor.highlightstrategies.tests;

import conexp.frontend.latticeeditor.tests.HighlightStrategyTest;
import conexp.frontend.latticeeditor.HighlightStrategy;
import conexp.frontend.latticeeditor.highlightstrategies.FilterHighlightStrategy;
import conexp.frontend.latticeeditor.highlightstrategies.IdealHighlightStrategy;

/**
 * User: sergey
 * Date: 18/4/2005
 * Time: 1:13:04
 */



public class FilterHighlightStrategyTest extends HighlightStrategyTest{
    protected HighlightStrategy makeNotEqualInstance() {
        return new IdealHighlightStrategy();
    }

    protected HighlightStrategy makeEqualInstance() {
        return new FilterHighlightStrategy();
    }
}