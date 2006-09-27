/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor.noderadiusstrategy;

import conexp.core.ConceptsCollection;
import conexp.core.stability.BruteForceIntegralStabilityCalculator;
import conexp.core.stability.IntegralStabilityCalculator;
import conexp.frontend.latticeeditor.ConceptQuery;
import conexp.frontend.latticeeditor.DrawParameters;

public class ConceptIntegralStabilityNodeRadiusCalcStrategy extends ConceptDependentRadiusCalcStrategy {
    IntegralStabilityCalculator stabilityCalculator = new BruteForceIntegralStabilityCalculator();

    public ConceptIntegralStabilityNodeRadiusCalcStrategy(DrawParameters opt) {
        super(opt);
    }

    public double calculateRatio(ConceptQuery query) {
        return stabilityCalculator.getIntegralStabilityForSet(query.getQueryIntent());
    }

    protected double calculateMaximalValue(ConceptsCollection conceptSet) {
        stabilityCalculator.setRelation(conceptSet.getContext().getRelation());
        return 1;
    }

}
