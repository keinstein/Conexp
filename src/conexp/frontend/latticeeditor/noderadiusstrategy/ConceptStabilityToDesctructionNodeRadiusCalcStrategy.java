/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor.noderadiusstrategy;

import conexp.core.ConceptsCollection;
import conexp.core.ExtendedContextEditingInterface;
import conexp.frontend.latticeeditor.ConceptQuery;
import conexp.frontend.latticeeditor.DrawParameters;

public class ConceptStabilityToDesctructionNodeRadiusCalcStrategy extends ConceptDependentRadiusCalcStrategy {

    public ConceptStabilityToDesctructionNodeRadiusCalcStrategy(DrawParameters opt) {
        super(opt);
    }

    public double calculateRatio(ConceptQuery query) {
        return ((double) query.getStability()) / getMaximalValue();
    }

    //TODO: change calculation of maximal value of query on calculation by figure drawing,
    // not by conexp set

    protected double calculateMaximalValue(ConceptsCollection conceptSet) {
        ExtendedContextEditingInterface context = conceptSet.getContext();
        final MaxParamValueConceptVisitor visitor = new MaxStabilityToDestructionConceptVisitor(context);
        conceptSet.forEach(visitor);
        return visitor.getMaxValue();
    }

}
