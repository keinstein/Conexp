/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.latticeeditor.labelingstrategies;

import conexp.frontend.latticeeditor.ConceptQuery;
import conexp.frontend.latticeeditor.DrawParameters;
import util.StringUtil;


public class OwnObjectsCountLabelingStrategy extends SimpleConceptLabelingStrategy {
    /**
     * OwnObjectsCountLabelingStrategy constructor comment.
     * @param opt conexp.frontend.latticeeditor.LatticePainterOptions
     */
    public OwnObjectsCountLabelingStrategy(DrawParameters opt) {
        super(opt);
    }

    /**
     * accept method comment.
     */
    public boolean accept(ConceptQuery concept) {
        return concept.isInnermost() && concept.hasOwnObjects();
    }

    /**
     * getDescriptionString method comment.
     */
    protected String getDescriptionString(ConceptQuery conceptQuery) {
        return conceptQuery.getOwnObjectsCount() + " ( " + StringUtil.formatPercents((double) conceptQuery.getOwnObjectsCount() / cxt.getObjectCount()) + " )";
    }
}
