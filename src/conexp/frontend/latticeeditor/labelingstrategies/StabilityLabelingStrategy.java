/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.latticeeditor.labelingstrategies;

import conexp.core.ContextFunctions;
import conexp.frontend.latticeeditor.ConceptQuery;
import conexp.frontend.latticeeditor.DrawParameters;


public class StabilityLabelingStrategy extends SimpleConceptLabelingStrategy {
    /**
     * OwnObjectsCountLabelingStrategy constructor comment.
     * @param opt conexp.frontend.latticeeditor.LatticePainterOptions
     */
    public StabilityLabelingStrategy(DrawParameters opt) {
        super(opt);
    }

    /**
     * accept method comment.
     */
    public boolean accept(ConceptQuery conceptQuery) {
        return conceptQuery.isInnermost();
    }

    /**
     * getDescriptionString method comment.
     */
    protected String getDescriptionString(ConceptQuery conceptQuery) {
        return "" + ContextFunctions.stability(conceptQuery.getQueryIntent(), cxt);
    }
}
