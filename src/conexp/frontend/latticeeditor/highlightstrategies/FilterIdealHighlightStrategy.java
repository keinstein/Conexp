/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor.highlightstrategies;

import conexp.core.Set;
import conexp.frontend.latticeeditor.ConceptHighlightStrategy;

public class FilterIdealHighlightStrategy extends ConceptHighlightStrategyBase{

    public ConceptHighlightStrategy createNew() {
        return new FilterIdealHighlightStrategy();
    }

    public boolean highlightQuery(Set attribs) {
        return query.isSubsetOf(attribs) || query.isSupersetOf(attribs);
    }

    public boolean highlightEdge(Set startAttribs, Set endAttribs) {
        return query.isSubsetOf(startAttribs) && query.isSubsetOf(endAttribs) ||
                query.isSupersetOf(startAttribs) && query.isSupersetOf(endAttribs);
    }

    public FilterIdealHighlightStrategy() {
    }
}
