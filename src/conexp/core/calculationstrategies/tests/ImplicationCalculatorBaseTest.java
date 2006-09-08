/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.calculationstrategies.tests;

import conexp.core.Context;
import conexp.core.ImplicationCalcStrategy;
import conexp.core.ImplicationSet;
import conexp.core.compareutils.ImplicationSetComparator;
import conexp.core.tests.ImplicationsBuilder;
import conexp.core.tests.SetBuilder;
import junit.framework.TestCase;


public abstract class ImplicationCalculatorBaseTest extends TestCase {
    private static void doTestPreparedImplicationCalcStrategy(ImplicationCalcStrategy calc, Context cxt, int[][][] expImplicationsDescriptions) {
        ImplicationSet implications = new ImplicationSet(cxt);
        calc.setImplications(implications);
        calc.calcImplications();
        ImplicationSet expImplication =
                ImplicationsBuilder.makeImplicationSet(cxt,
                        expImplicationsDescriptions);
        if (!expImplication.equalsToIsomorphism(implications)) {
            ImplicationSetComparator comparator = new ImplicationSetComparator(expImplication, implications);
            comparator.dumpDifferencesToSout();
            assertEquals(expImplication, implications);
        }


    }

    protected abstract ImplicationCalcStrategy makePreparedCalculator(Context cxt);

    protected void doTestImplicationCalculator(int[][] relation, int[][][] expImplicationsDescriptions) {
        Context cxt = SetBuilder.makeContext(relation);
        ImplicationCalcStrategy calc = makePreparedCalculator(cxt);
        doTestPreparedImplicationCalcStrategy(calc, cxt, expImplicationsDescriptions);
    }

}
