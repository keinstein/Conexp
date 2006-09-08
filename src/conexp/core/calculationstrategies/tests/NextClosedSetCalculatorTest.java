/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.calculationstrategies.tests;

import conexp.core.ConceptCalcStrategy;
import conexp.core.ConceptsCollection;
import conexp.core.ItemSet;
import conexp.core.calculationstrategies.NextClosedSetCalculator;
import conexp.core.enumcallbacks.ConceptSetCallback;
import conexp.core.tests.SetBuilder;


public class NextClosedSetCalculatorTest extends EnumerativeCalcStrategyTest {

    protected ConceptCalcStrategy makeCalcStrategy() {
        return new NextClosedSetCalculator();
    }

    public static void testOrder() {
        NextClosedSetCalculator calc = new NextClosedSetCalculator();
        calc.setRelation(SetBuilder.makeRelation(new int[][]{{1, 0, 0},
                {1, 1, 0},
                {0, 0, 1}}));

        ConceptsCollection col = new ConceptsCollection();
        ConceptSetCallback callback = new ConceptSetCallback(col);
        calc.setCallback(callback);
        calc.calculateConceptSet();

        for (int i = col.conceptsCount(); --i >= 1;) {
            ItemSet curr = col.conceptAt(i);
            ItemSet prev = col.conceptAt(i - 1);
            assertEquals(1, curr.getAttribs().lexCompareGanter(prev.getAttribs()));
        }
    }

}
