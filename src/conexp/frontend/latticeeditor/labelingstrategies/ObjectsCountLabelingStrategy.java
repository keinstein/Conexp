/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/

package conexp.frontend.latticeeditor.labelingstrategies;

import conexp.frontend.latticeeditor.ConceptQuery;
import conexp.frontend.latticeeditor.DrawParameters;
import util.StringUtil;


public class ObjectsCountLabelingStrategy extends SimpleConceptLabelingStrategy {

    /**
     * ObjectsCountLabellingStrategy constructor comment.
     * @param opt conexp.frontend.latticeeditor.LatticePainterDrawParams
     */
    public ObjectsCountLabelingStrategy(DrawParameters opt) {
        super(opt);
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
     * @return java.lang.String
     * @param concept conexp.core.LatticeElement
     */
    protected String getDescriptionString(ConceptQuery conceptQuery) {
        return conceptQuery.getExtentSize() + " / " + StringUtil.formatPercents((double) conceptQuery.getExtentSize() / cxt.getObjectCount());
    }
}
