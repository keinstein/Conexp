/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.core.calculationstrategies.tests;

import conexp.core.ItemSet;
import conexp.core.calculationstrategies.NextClosedSetCalculator;
import conexp.core.tests.SetBuilder;
import junit.framework.Test;
import junit.framework.TestSuite;


public class NextClosedSetCalculatorTest extends EnumerativeCalcStrategyTest {
    private static final Class THIS = NextClosedSetCalculatorTest.class;

    protected conexp.core.ConceptCalcStrategy makeCalcStrategy() {
        return new NextClosedSetCalculator();
    }


    public static Test suite() {
        return new TestSuite(THIS);
    }

    public void testOrder() {
        NextClosedSetCalculator calc = new NextClosedSetCalculator();
        calc.setRelation(SetBuilder.makeRelation(new int[][]{{1, 0, 0},
                                                             {1, 1, 0},
                                                             {0, 0, 1}}));

        conexp.core.ConceptsCollection col = new conexp.core.ConceptsCollection();
        conexp.core.enumcallbacks.ConceptSetCallback callback = new conexp.core.enumcallbacks.ConceptSetCallback(col);
        calc.setCallback(callback);
        calc.calculateConceptSet();

        for (int i = col.conceptsCount(); --i >= 1;) {
            ItemSet curr = col.conceptAt(i);
            ItemSet prev = col.conceptAt(i - 1);
            assertEquals(1, curr.getAttribs().lexCompareGanter(prev.getAttribs()));
        }
    }

}
