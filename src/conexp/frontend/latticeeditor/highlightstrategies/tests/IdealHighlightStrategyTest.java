package conexp.frontend.latticeeditor.highlightstrategies.tests;

import conexp.frontend.latticeeditor.highlightstrategies.FilterIdealHighlightStrategy;
import conexp.frontend.latticeeditor.highlightstrategies.IdealHighlightStrategy;
import conexp.frontend.latticeeditor.tests.HighlightStrategyTest;
import canvas.IHighlightStrategy;

/**
 * User: sergey
 * Date: 18/4/2005
 * Time: 1:13:04
 */


public class IdealHighlightStrategyTest extends HighlightStrategyTest {
    protected IHighlightStrategy makeNotEqualInstance() {
        return new FilterIdealHighlightStrategy();
    }

    protected IHighlightStrategy makeEqualInstance() {
        return new IdealHighlightStrategy();
    }
}