/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.latticeeditor.highlightstrategies;

import conexp.core.Set;
import conexp.frontend.latticeeditor.HighlightStrategy;


public class NeigboursHighlightStrategy extends OneNodeHighlightStrategy {

    protected HighlightStrategy createNew() {
        return new NeigboursHighlightStrategy();
    }

    protected boolean doHighlightEdge(Set startAttribs, Set endAttribs) {
        return highlightQuery(startAttribs) || highlightQuery(endAttribs);
    }

    public NeigboursHighlightStrategy() {
    }
}
