/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor.highlightstrategies;

import conexp.core.Set;
import conexp.frontend.latticeeditor.ConceptHighlightStrategy;

public class OneNodeHighlightStrategy extends ConceptHighlightStrategyBase {

    public ConceptHighlightStrategy createNew() {
        return new OneNodeHighlightStrategy();
    }

    public boolean highlightQuery(Set attribs) {
        return isQueryAttributes(attribs);
    }

    protected boolean isQueryAttributes(Set attribs) {
        return query.equals(attribs);
    }


    public boolean highlightEdge(Set startAttribs, Set endAttribs) {
        return false;
    }

    public OneNodeHighlightStrategy() {

    }
}
