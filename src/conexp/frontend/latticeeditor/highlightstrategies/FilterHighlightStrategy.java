package conexp.frontend.latticeeditor.highlightstrategies;

import conexp.core.Set;

/**
 * Insert the type's description here.
 * Creation date: (02.12.00 13:28:39)
 * @author
 */
public class FilterHighlightStrategy extends conexp.frontend.latticeeditor.HighlightStrategy {

    protected boolean highlightQuery(Set attribs) {
        return query.isSupersetOf(attribs);
    }

    protected boolean doHighlightEdge(Set startAttribs, Set endAttribs) {
        return highlightQuery(startAttribs) && highlightQuery(endAttribs);
    }

    public FilterHighlightStrategy() {
        super();
    }
}