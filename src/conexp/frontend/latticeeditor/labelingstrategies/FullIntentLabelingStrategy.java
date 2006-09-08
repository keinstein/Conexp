/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor.labelingstrategies;

import conexp.frontend.latticeeditor.ConceptQuery;

public class FullIntentLabelingStrategy extends BaseFullLabelingStrategy {
    public FullIntentLabelingStrategy() {
        super();
    }

    protected String getDescriptionString(ConceptQuery conceptQuery) {
        StringBuffer ret = new StringBuffer();
        describeContextEntitySet(ret, conceptQuery.intentIterator());
        return ret.toString();
    }

}
