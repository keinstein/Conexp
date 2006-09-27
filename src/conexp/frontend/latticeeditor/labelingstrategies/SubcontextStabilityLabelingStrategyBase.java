/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.frontend.latticeeditor.labelingstrategies;

import conexp.core.BinaryRelationProcessor;
import conexp.core.ExtendedContextEditingInterface;
import conexp.frontend.latticeeditor.ConceptQuery;
import conexp.frontend.latticeeditor.ConceptSetDrawing;
import util.StringUtil;



public abstract class SubcontextStabilityLabelingStrategyBase extends OneLabelConceptLabelingStrategy {
    BinaryRelationProcessor stabilityCalculator;

    protected abstract BinaryRelationProcessor makeStabilityCalculator();

    protected SubcontextStabilityLabelingStrategyBase() {
        super();
        stabilityCalculator = makeStabilityCalculator();
    }

    public boolean accept(ConceptQuery conceptQuery) {
        return conceptQuery.isInnermost();
    }

    public void setContext(ExtendedContextEditingInterface cxt) {
        super.setContext(cxt);
        stabilityCalculator.setRelation(cxt.getRelation());
    }

    public void shutdown(ConceptSetDrawing drawing) {
        super.shutdown(drawing);
        stabilityCalculator.tearDown();
    }

    protected String getDescriptionString(ConceptQuery conceptQuery) {
        return "" + StringUtil.formatPercents(getStabilityValue(conceptQuery), 2);
    }

    protected abstract double getStabilityValue(ConceptQuery conceptQuery);
}
