package conexp.frontend.latticeeditor.noderadiusstrategy;

import conexp.frontend.latticeeditor.ConceptQuery;
import conexp.frontend.latticeeditor.DrawParameters;

public class OwnObjectsVolumeNodeRadiusCalcStrategy extends ConceptDependentRadiusCalcStrategy {

    public OwnObjectsVolumeNodeRadiusCalcStrategy(DrawParameters opt) {
        super(opt);
    }

    protected double calculatePercents(ConceptQuery query) {
        return query.getOwnObjectsCount() / getMaximalValue();
    }

    protected int calculateMaximalValue(conexp.core.ConceptsCollection conceptSet) {
        final MaxParamValueConceptVisitor visitor = new MaxOwnObjectCountConceptVisitor();
        conceptSet.forEach(visitor);
        return visitor.getMaxValue();
    }
}