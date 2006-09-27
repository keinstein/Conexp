/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor.noderadiusstrategy;

import conexp.frontend.latticeeditor.ConceptQuery;
import conexp.frontend.latticeeditor.DrawParameters;

public class OwnObjectsVolumeNodeRadiusCalcStrategy extends ConceptDependentRadiusCalcStrategy {

    public OwnObjectsVolumeNodeRadiusCalcStrategy(DrawParameters opt) {
        super(opt);
    }

    public double calculateRatio(ConceptQuery query) {
        return query.getOwnObjectsCount() / getMaximalValue();
    }

    protected double calculateMaximalValue(conexp.core.ConceptsCollection conceptSet) {
        final MaxParamValueConceptVisitor visitor = new MaxOwnObjectCountConceptVisitor();
        conceptSet.forEach(visitor);
        return visitor.getMaxValue();
    }
}
