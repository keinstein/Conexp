/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.frontend.latticeeditor.edgesizecalcstrategies.tests;



import conexp.frontend.latticeeditor.BasicDrawParams;
import conexp.frontend.latticeeditor.DefaultDimensionCalcStrategy;
import conexp.frontend.latticeeditor.LatticePainterDrawParams;
import conexp.frontend.latticeeditor.edgesizecalcstrategies.ObjectFlowEdgeSizeCalcStrategy;
import conexp.frontend.latticeeditor.tests.DefaultDimensionCalcStrategyTest;

public class ObjectFlowEdgeSizeCalcStrategyTest extends DefaultDimensionCalcStrategyTest {

    protected DefaultDimensionCalcStrategy makeNotEqualInstance() {
        return new ObjectFlowEdgeSizeCalcStrategy(new LatticePainterDrawParams());
    }

    protected DefaultDimensionCalcStrategy makeEqualInstance() {
        return new ObjectFlowEdgeSizeCalcStrategy(BasicDrawParams.getInstance());
    }
}
