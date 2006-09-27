/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor.labelingstrategies;

import conexp.core.ContextFunctions;
import conexp.frontend.latticeeditor.ConceptQuery;


public class StabilityToDesctructionLabelingStrategy extends OneLabelConceptLabelingStrategy {
    public StabilityToDesctructionLabelingStrategy() {
        super();
    }

    public boolean accept(ConceptQuery conceptQuery) {
        return conceptQuery.isInnermost();
    }

    protected String getDescriptionString(ConceptQuery conceptQuery) {
        return "" + ContextFunctions.stabilityToDesctruction(conceptQuery.getQueryIntent(), cxt);
    }
}
