/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor.labelingstrategies;

import conexp.core.ContextEntity;
import conexp.frontend.latticeeditor.ConceptQuery;

import java.util.Iterator;


public abstract class BaseFullLabelingStrategy extends OneLabelConceptLabelingStrategy {
    protected BaseFullLabelingStrategy() {
        super();
    }

    protected static void describeContextEntitySet(StringBuffer ret, Iterator iter) {
        ret.append("{ ");
        boolean first = true;
        while (iter.hasNext()) {
            if (first) {
                first = false;
            } else {
                ret.append(" , ");
            }
            ret.append(((ContextEntity) iter.next()).getName());
        }
        ret.append(" }");
    }

    public boolean accept(ConceptQuery query) {
        return true;
    }
}
