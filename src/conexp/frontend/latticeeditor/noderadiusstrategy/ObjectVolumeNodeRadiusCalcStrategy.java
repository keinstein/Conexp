/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor.noderadiusstrategy;

import conexp.core.ConceptsCollection;
import conexp.frontend.latticeeditor.ConceptQuery;
import conexp.frontend.latticeeditor.DrawParameters;


public class ObjectVolumeNodeRadiusCalcStrategy extends ConceptDependentRadiusCalcStrategy {
    public ObjectVolumeNodeRadiusCalcStrategy(DrawParameters opt) {
        super(opt);
    }

    public double calculateRatio(ConceptQuery query) {
        return query.getExtentSize() / getMaximalValue();
    }

    protected double calculateMaximalValue(ConceptsCollection conceptSet) {
        return conceptSet.getContext().getObjectCount();
    }
}
