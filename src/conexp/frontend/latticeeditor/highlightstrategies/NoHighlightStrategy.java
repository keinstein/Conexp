package conexp.frontend.latticeeditor.highlightstrategies;

import conexp.core.Set;
import conexp.frontend.latticeeditor.HighlightStrategy;


/**
 * Insert the type's description here.
 * Creation date: (01.12.00 5:23:57)
 * @author
 */
public class NoHighlightStrategy extends HighlightStrategy {

    protected boolean doHighlightEdge(Set startAttribs, Set endAttribs) {
        return false;
    }

    protected boolean highlightQuery(Set attribs) {
        return false;
    }

    public boolean isActive() {
        return false;
    }

    public NoHighlightStrategy() {
    }
}