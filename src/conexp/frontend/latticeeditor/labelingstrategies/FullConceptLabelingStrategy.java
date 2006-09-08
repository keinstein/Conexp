/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor.labelingstrategies;

import conexp.frontend.latticeeditor.ConceptQuery;

public class FullConceptLabelingStrategy extends BaseFullLabelingStrategy {
    public FullConceptLabelingStrategy() {
        super();
    }

    protected String getDescriptionString(ConceptQuery conceptQuery) {
        StringBuffer ret = new StringBuffer();
        ret.append('(');
        describeContextEntitySet(ret, conceptQuery.extentIterator());
        ret.append(" , ");

        describeContextEntitySet(ret, conceptQuery.intentIterator());
        ret.append(" )");
        return ret.toString();
    }
}
