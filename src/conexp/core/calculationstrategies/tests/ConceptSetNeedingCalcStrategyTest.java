package conexp.core.calculationstrategies.tests;

import conexp.core.BinaryRelation;
import conexp.core.ConceptSetNeedingCalcStrategy;
import conexp.core.ConceptsCollection;

public abstract class ConceptSetNeedingCalcStrategyTest extends CalcStrategyTest {

    protected ConceptSetNeedingCalcStrategy getRealStrategy() {
        return (ConceptSetNeedingCalcStrategy) calcStrategy;
    }

    protected void buildIntentsSetAndFillExpectationSet(BinaryRelation rel, com.mockobjects.ExpectationSet expSet, com.mockobjects.ExpectationSet expSetExtents, int expectedEdgeCount) {
        ConceptsCollection col = new ConceptsCollection();
        setupStrategy(col);
        generateIntents();
        ConceptSetTestUtils.fillExpectationSetByIntentsFromLattice(expSet, col);
        ConceptSetTestUtils.fillExpectationSetByExtentsFromLattice(expSetExtents, col);
    }

    protected void setupStrategy(ConceptsCollection col) {
        getRealStrategy().setConceptSet(col);
    }
}