package conexp.frontend.latticeeditor.labelingstrategies;

import conexp.frontend.latticeeditor.DrawParameters;
import conexp.frontend.latticeeditor.ConceptQuery;
import conexp.frontend.latticeeditor.ConceptSetDrawing;
import conexp.core.ExtendedContextEditingInterface;
import conexp.core.BinaryRelationProcessor;
import conexp.core.stability.PointAndIntegralStabilityCalculator;
import util.StringUtil;

/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 */

public abstract class SubcontextStabilityLabelingStrategyBase extends SimpleConceptLabelingStrategy {
    BinaryRelationProcessor stabilityCalculator;

    protected abstract BinaryRelationProcessor makeStabilityCalculator();

    public SubcontextStabilityLabelingStrategyBase(DrawParameters opt) {
        super(opt);
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
        return "" + StringUtil.formatPercents(getStabilityValue(conceptQuery));
    }

    protected abstract double getStabilityValue(ConceptQuery conceptQuery);
}
