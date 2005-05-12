package conexp.core.calculationstrategies.tests;

/**
 * User: sergey
 * Date: 12/5/2005
 * Time: 21:08:27
 */

import conexp.core.Context;
import conexp.core.ImplicationCalcStrategy;
import conexp.core.calculationstrategies.AttributeIncrementalImplicationCalculator;

public class AttributeIncrementalImplicationCalculatorTest extends ImplicationCalculatorTest{
    protected ImplicationCalcStrategy makePreparedCalculator(Context cxt) {

             ImplicationCalcStrategy calc = new AttributeIncrementalImplicationCalculator();
        calc.setRelation(cxt.getRelation());
        return calc;
    }


}