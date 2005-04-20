/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.latticeeditor.highlightstrategies;

import conexp.core.Set;
import conexp.frontend.latticeeditor.HighlightStrategy;

public class OneNodeHighlightStrategy extends HighlightStrategy {

    protected HighlightStrategy createNew() {
        return new OneNodeHighlightStrategy();
    }

    protected boolean highlightQuery(Set attribs) {
        return query.equals(attribs);
    }

    protected boolean doHighlightEdge(Set startAttribs, Set endAttribs) {
        return false;
    }

    public OneNodeHighlightStrategy() {

    }
}
