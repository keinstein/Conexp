package conexp.frontend.latticeeditor.highlightstrategies.tests;

import canvas.IHighlightStrategy;
import conexp.frontend.latticeeditor.highlightstrategies.FilterHighlightStrategy;
import conexp.frontend.latticeeditor.highlightstrategies.IdealHighlightStrategy;
import conexp.frontend.latticeeditor.tests.HighlightStrategyTest;

/**
 * User: sergey
 * Date: 18/4/2005
 * Time: 1:13:04
 */


public class FilterHighlightStrategyTest extends HighlightStrategyTest {
    protected IHighlightStrategy makeNotEqualInstance() {
        return new IdealHighlightStrategy();
    }

    protected IHighlightStrategy makeEqualInstance() {
        return new FilterHighlightStrategy();
    }
}