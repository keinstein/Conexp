package conexp.frontend.latticeeditor.highlightstrategies;

import conexp.core.Set;

public class IdealHighlightStrategy extends conexp.frontend.latticeeditor.HighlightStrategy {

    protected boolean highlightQuery(Set attribs) {
        return query.isSubsetOf(attribs);
    }

    protected boolean doHighlightEdge(Set startAttribs, Set endAttribs) {
        return highlightQuery(startAttribs) && highlightQuery(endAttribs);
    }

    public IdealHighlightStrategy() {
    }
}