/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/

package conexp.frontend.latticeeditor.highlightstrategies;

import conexp.core.Set;


public class NeigboursHighlightStrategy extends OneNodeHighlightStrategy {

    /**
     * Insert the method's description here.
     * Creation date: (02.12.00 1:15:36)
     * @return boolean
     * @param e conexp.core.Edge
     */
    protected boolean doHighlightEdge(Set startAttribs, Set endAttribs) {
        return highlightQuery(startAttribs) || highlightQuery(endAttribs);
    }

    /**
     * NeigboursHighlightStrategy constructor comment.
     */
    public NeigboursHighlightStrategy() {
    }
}
