/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.latticeeditor.noderadiusstrategy;

import conexp.core.ConceptsCollection;
import conexp.core.ExtendedContextEditingInterface;
import conexp.core.BinaryRelation;
import conexp.core.stability.PointAndIntegralStabilityCalculator;
import conexp.frontend.latticeeditor.ConceptQuery;
import conexp.frontend.latticeeditor.DrawParameters;

public class ConceptPointStabilityNodeRadiusCalcStrategy extends ConceptDependentRadiusCalcStrategy {

    public ConceptPointStabilityNodeRadiusCalcStrategy(DrawParameters opt) {
        super(opt);
    }

    PointAndIntegralStabilityCalculator  stabilityCalculator = new PointAndIntegralStabilityCalculator();

    protected double calculatePercents(ConceptQuery query) {
        return stabilityCalculator.getPointStabilityOfSet(query.getQueryIntent());
    }

    protected int calculateMaximalValue(ConceptsCollection conceptSet) {
        stabilityCalculator.setRelation(conceptSet.getContext().getRelation());
        return 1;
    }

}
