/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
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

    protected double calculatePercents(ConceptQuery query) {
        return query.getExtentSize() / getMaximalValue();
    }

    protected int calculateMaximalValue(ConceptsCollection conceptSet) {
        return conceptSet.getContext().getObjectCount();
    }
}
