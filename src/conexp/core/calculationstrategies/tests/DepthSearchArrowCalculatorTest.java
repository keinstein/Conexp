/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.core.calculationstrategies.tests;

import conexp.core.ArrowCalculator;
import conexp.core.Context;
import conexp.core.calculationstrategies.DepthSearchArrowCalculator;
import junit.framework.TestCase;

public class DepthSearchArrowCalculatorTest extends TestCase {
    public void testDoCalcDownArrow() {
        Context cxt = new Context(200, 12);
        ArrowCalculator arrowCalc = new DepthSearchArrowCalculator();
        cxt.setArrowCalculator(arrowCalc);
        cxt.getDownArrow();
    }

    public void testDoCalcDownArrow2() {
        Context cxt = new Context(12, 200);
        ArrowCalculator arrowCalc = new DepthSearchArrowCalculator();
        cxt.setArrowCalculator(arrowCalc);
        cxt.getDownArrow();
    }


    public void testDoCalcUpArrow() {
        Context cxt = new Context(200, 14);
        ArrowCalculator arrowCalc = new DepthSearchArrowCalculator();
        cxt.setArrowCalculator(arrowCalc);
        cxt.getUpArrow();
    }

    public void testDoCalcUpArrow2() {
        Context cxt = new Context(12, 200);
        ArrowCalculator arrowCalc = new DepthSearchArrowCalculator();
        cxt.setArrowCalculator(arrowCalc);
        cxt.getUpArrow();
    }

}
