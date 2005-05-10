package conexp.frontend.latticeeditor.highlightstrategies.tests;

import conexp.frontend.latticeeditor.HighlightStrategy;
import conexp.frontend.latticeeditor.highlightstrategies.FilterIdealHighlightStrategy;
import conexp.frontend.latticeeditor.highlightstrategies.NoHighlightStrategy;
import conexp.frontend.latticeeditor.tests.HighlightStrategyTest;

/**
 * User: sergey
 * Date: 18/4/2005
 * Time: 1:13:04
 */


public class NoHighlightStrategyTest extends HighlightStrategyTest {
    protected HighlightStrategy makeNotEqualInstance() {
        return new FilterIdealHighlightStrategy();
    }

    protected HighlightStrategy makeEqualInstance() {
        return new NoHighlightStrategy();
    }
}