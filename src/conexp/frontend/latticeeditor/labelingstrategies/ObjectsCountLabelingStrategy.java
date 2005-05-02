/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.latticeeditor.labelingstrategies;

import conexp.frontend.latticeeditor.ConceptQuery;
import util.StringUtil;


public class ObjectsCountLabelingStrategy extends SimpleConceptLabelingStrategy {

    /**
     * ObjectsCountLabellingStrategy constructor comment.
     *
     * @param opt conexp.frontend.latticeeditor.LatticePainterDrawParams
     */
    public ObjectsCountLabelingStrategy() {
        super();
    }

    /**
     * accept method comment.
     */
    public boolean accept(ConceptQuery query) {
        return query.isInnermost();
    }

    /**
     * Insert the method's description here.
     * Creation date: (26.12.00 0:57:50)
     *
     * @param concept conexp.core.LatticeElement
     * @return java.lang.String
     */
    protected String getDescriptionString(ConceptQuery conceptQuery) {
        return conceptQuery.getExtentSize() + " / " + StringUtil.formatPercents((double) conceptQuery.getExtentSize() / cxt.getObjectCount());
    }
}
