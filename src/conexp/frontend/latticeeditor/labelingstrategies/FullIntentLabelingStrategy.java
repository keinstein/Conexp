/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.latticeeditor.labelingstrategies;

import conexp.frontend.latticeeditor.ConceptQuery;
import conexp.frontend.latticeeditor.DrawParameters;

public class FullIntentLabelingStrategy extends BaseFullLabelingStrategy {
    public FullIntentLabelingStrategy(DrawParameters opt) {
        super(opt);
    }

    protected String getDescriptionString(ConceptQuery conceptQuery) {
        StringBuffer ret = new StringBuffer();
        describeContextEntitySet(ret, conceptQuery.intentIterator());
        return ret.toString();
    }

}
