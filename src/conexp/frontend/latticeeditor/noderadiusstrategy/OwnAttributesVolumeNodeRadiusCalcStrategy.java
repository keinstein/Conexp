/*
 * $Id$
 * Copyright (c) 2005 realtime (http://www.realtime.dk),
 * All Rights Reserved.
 */
package conexp.frontend.latticeeditor.noderadiusstrategy;

import conexp.frontend.latticeeditor.DrawParameters;
import conexp.frontend.latticeeditor.ConceptQuery;

public class OwnAttributesVolumeNodeRadiusCalcStrategy extends ConceptDependentRadiusCalcStrategy {

    public OwnAttributesVolumeNodeRadiusCalcStrategy(DrawParameters opt) {
        super(opt);
    }

    public double calculateRatio(ConceptQuery query) {
        return query.getOwnAttribsCount()/ getMaximalValue();
    }

    protected double calculateMaximalValue(conexp.core.ConceptsCollection conceptSet) {
        final MaxParamValueConceptVisitor visitor = new MaxOwnAttributeCountConceptVisitor();
        conceptSet.forEach(visitor);
        return visitor.getMaxValue();
    }
}
