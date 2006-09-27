/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor.labelingstrategies;

import conexp.frontend.latticeeditor.ConceptQuery;
import util.StringUtil;


public class OwnObjectsCountLabelingStrategy extends OneLabelConceptLabelingStrategy {
    public OwnObjectsCountLabelingStrategy() {
        super();
    }

    public boolean accept(ConceptQuery concept) {
        return concept.isInnermost() && concept.hasOwnObjects();
    }

    protected String getDescriptionString(ConceptQuery conceptQuery) {
        return conceptQuery.getOwnObjectsCount() + " ( " + StringUtil.formatPercents((double) conceptQuery.getOwnObjectsCount() / cxt.getObjectCount()) + " )";
    }
}
