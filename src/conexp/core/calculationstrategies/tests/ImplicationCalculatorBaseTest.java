package conexp.core.calculationstrategies.tests;

import junit.framework.TestCase;
import conexp.core.ImplicationCalcStrategy;
import conexp.core.Context;
import conexp.core.ImplicationSet;
import conexp.core.tests.SetBuilder;

/**
 * Author: Serhiy Yevtushenko
 * Date: 20.01.2003
 * Time: 0:36:55
 */
public abstract class ImplicationCalculatorBaseTest extends TestCase {
    protected static void doTestPreparedImplicationCalcStrategy(ImplicationCalcStrategy calc, Context cxt, int[][][] expImplicationsDescriptions) {
        ImplicationSet implications = new ImplicationSet(cxt);
        calc.setImplications(implications);
        calc.calcDuquenneGuiguiesSet();
        ImplicationSet expImplication =
                SetBuilder.makeImplicationSet(cxt,
                        expImplicationsDescriptions);
//        assertEquals(expImplication.size(), implications.size());
        assertEquals(expImplication, implications);
    }

    protected abstract ImplicationCalcStrategy makePreparedCalculator(Context cxt);

    protected void doTestImplicationCalculator(int[][] relation, int[][][] expImplicationsDescriptions) {
        Context cxt = SetBuilder.makeContext(relation);
        ImplicationCalcStrategy calc = makePreparedCalculator(cxt);
        doTestPreparedImplicationCalcStrategy(calc, cxt, expImplicationsDescriptions);
    }

}
