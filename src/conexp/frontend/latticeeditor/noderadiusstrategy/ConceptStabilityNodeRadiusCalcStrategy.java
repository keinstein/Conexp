/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.latticeeditor.noderadiusstrategy;

import conexp.core.ConceptsCollection;
import conexp.core.ExtendedContextEditingInterface;
import conexp.frontend.latticeeditor.ConceptQuery;
import conexp.frontend.latticeeditor.DrawParameters;

public class ConceptStabilityNodeRadiusCalcStrategy extends ConceptDependentRadiusCalcStrategy {

    public ConceptStabilityNodeRadiusCalcStrategy(DrawParameters opt) {
        super(opt);
    }

    protected double calculatePercents(ConceptQuery query) {
        return ((double) query.getStability()) / getMaximalValue();
    }

    //TODO: change calculation of maximal value of query on calculation by figure drawing,
    // not by conexp set

    protected int calculateMaximalValue(ConceptsCollection conceptSet) {
        ExtendedContextEditingInterface context = conceptSet.getContext();
        final MaxParamValueConceptVisitor visitor = new MaxStabilityConceptVisitor(context);
        conceptSet.forEach(visitor);
        return visitor.getMaxValue();
    }

}
