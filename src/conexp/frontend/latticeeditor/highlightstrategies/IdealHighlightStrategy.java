/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/

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
