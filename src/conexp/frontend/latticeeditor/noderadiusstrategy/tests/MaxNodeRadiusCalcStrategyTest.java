package conexp.frontend.latticeeditor.noderadiusstrategy.tests;

/**
 * User: sergey
 * Date: 17/4/2005
 * Time: 16:54:14
 */

import conexp.frontend.latticeeditor.BasicDrawParams;
import conexp.frontend.latticeeditor.DefaultDimensionCalcStrategy;
import conexp.frontend.latticeeditor.LatticePainterDrawParams;
import conexp.frontend.latticeeditor.noderadiusstrategy.MaxNodeRadiusCalcStrategy;
import conexp.frontend.latticeeditor.tests.DefaultDimensionCalcStrategyTest;

public class MaxNodeRadiusCalcStrategyTest extends DefaultDimensionCalcStrategyTest {

    protected DefaultDimensionCalcStrategy  makeEqualInstance() {
        return new MaxNodeRadiusCalcStrategy(BasicDrawParams.getInstance());
    }

    protected DefaultDimensionCalcStrategy makeNotEqualInstance() {
        return new MaxNodeRadiusCalcStrategy(new LatticePainterDrawParams());
    }
}