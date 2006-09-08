/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.frontend.latticeeditor.noderadiusstrategy.tests;



import conexp.frontend.latticeeditor.BasicDrawParams;
import conexp.frontend.latticeeditor.DefaultDimensionCalcStrategy;
import conexp.frontend.latticeeditor.LatticePainterDrawParams;
import conexp.frontend.latticeeditor.noderadiusstrategy.MaxNodeRadiusCalcStrategy;
import conexp.frontend.latticeeditor.tests.DefaultDimensionCalcStrategyTest;

public class MaxNodeRadiusCalcStrategyTest extends DefaultDimensionCalcStrategyTest {

    protected DefaultDimensionCalcStrategy makeEqualInstance() {
        return new MaxNodeRadiusCalcStrategy(BasicDrawParams.getInstance());
    }

    protected DefaultDimensionCalcStrategy makeNotEqualInstance() {
        return new MaxNodeRadiusCalcStrategy(new LatticePainterDrawParams());
    }
}
