/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.calculationstrategies.tests;

import conexp.core.ArrowCalculator;
import conexp.core.Context;
import conexp.core.calculationstrategies.DepthSearchArrowCalculator;
import junit.framework.TestCase;

public class DepthSearchArrowCalculatorTest extends TestCase {
    public static void testDoCalcDownArrow() {
        Context cxt = new Context(200, 12);
        ArrowCalculator arrowCalc = new DepthSearchArrowCalculator();
        cxt.setArrowCalculator(arrowCalc);
        cxt.getDownArrow();
    }

    public static void testDoCalcDownArrow2() {
        Context cxt = new Context(12, 200);
        ArrowCalculator arrowCalc = new DepthSearchArrowCalculator();
        cxt.setArrowCalculator(arrowCalc);
        cxt.getDownArrow();
    }


    public static void testDoCalcUpArrow() {
        Context cxt = new Context(200, 14);
        ArrowCalculator arrowCalc = new DepthSearchArrowCalculator();
        cxt.setArrowCalculator(arrowCalc);
        cxt.getUpArrow();
    }

    public static void testDoCalcUpArrow2() {
        Context cxt = new Context(12, 200);
        ArrowCalculator arrowCalc = new DepthSearchArrowCalculator();
        cxt.setArrowCalculator(arrowCalc);
        cxt.getUpArrow();
    }

}
