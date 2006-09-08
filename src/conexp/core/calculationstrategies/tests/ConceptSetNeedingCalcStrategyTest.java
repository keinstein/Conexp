/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.calculationstrategies.tests;

import com.mockobjects.ExpectationSet;
import conexp.core.BinaryRelation;
import conexp.core.ConceptSetNeedingCalcStrategy;
import conexp.core.ConceptsCollection;

public abstract class ConceptSetNeedingCalcStrategyTest extends CalcStrategyTest {

    protected ConceptSetNeedingCalcStrategy getRealStrategy() {
        return (ConceptSetNeedingCalcStrategy) calcStrategy;
    }

    protected void buildIntentsSetAndFillExpectationSet(BinaryRelation rel, ExpectationSet expSet, ExpectationSet expSetExtents) {
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
