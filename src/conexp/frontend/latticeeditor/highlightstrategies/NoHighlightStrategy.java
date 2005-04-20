/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.latticeeditor.highlightstrategies;

import conexp.core.Set;
import conexp.frontend.latticeeditor.HighlightStrategy;


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

    protected HighlightStrategy createNew() {
        return new NoHighlightStrategy();
    }


}
