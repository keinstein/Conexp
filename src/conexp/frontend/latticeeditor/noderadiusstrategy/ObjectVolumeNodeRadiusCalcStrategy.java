package conexp.frontend.latticeeditor.noderadiusstrategy;

import conexp.core.ConceptsCollection;
import conexp.frontend.latticeeditor.ConceptQuery;
import conexp.frontend.latticeeditor.DrawParameters;

/**
 * Insert the type's description here.
 * Creation date: (12.10.00 20:29:48)
 */
public class ObjectVolumeNodeRadiusCalcStrategy extends ConceptDependentRadiusCalcStrategy {
    public ObjectVolumeNodeRadiusCalcStrategy(DrawParameters opt) {
        super(opt);
    }

    protected double calculatePercents(ConceptQuery query) {
        return query.getExtentSize()/ getMaximalValue();
    }

    protected int calculateMaximalValue(ConceptsCollection conceptSet) {
        return conceptSet.getContext().getObjectCount();
    }
}