/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.latticeeditor.labelingstrategies;

import conexp.core.ContextFunctions;
import conexp.frontend.latticeeditor.ConceptQuery;
import conexp.frontend.latticeeditor.DrawParameters;


public class StabilityToDesctructionLabelingStrategy extends SimpleConceptLabelingStrategy {
    public StabilityToDesctructionLabelingStrategy(DrawParameters opt) {
        super(opt);
    }

    public boolean accept(ConceptQuery conceptQuery) {
        return conceptQuery.isInnermost();
    }

    protected String getDescriptionString(ConceptQuery conceptQuery) {
        return "" + ContextFunctions.stabilityToDesctruction(conceptQuery.getQueryIntent(), cxt);
    }
}
