package conexp.frontend.latticeeditor;

import conexp.util.GenericStrategy;

public interface DimensionCalcStrategy extends GenericStrategy {
    void initCalc();

    void setConceptSet(conexp.core.ConceptsCollection conceptSet);
}