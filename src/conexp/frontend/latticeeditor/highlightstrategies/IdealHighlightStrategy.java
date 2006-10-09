/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor.highlightstrategies;

import conexp.core.Set;
import conexp.frontend.latticeeditor.ConceptHighlightStrategy;

public class IdealHighlightStrategy extends ConceptHighlightStrategyBase  {

    public ConceptHighlightStrategy createNew() {
        return new IdealHighlightStrategy();
    }

    public boolean highlightQuery(Set attribs) {
        return query.isSubsetOf(attribs);
    }

    public boolean highlightEdge(Set startAttribs, Set endAttribs) {
        return highlightQuery(startAttribs) && highlightQuery(endAttribs);
    }

    public IdealHighlightStrategy() {
    }
}
