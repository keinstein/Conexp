package conexp.frontend.latticeeditor.edgesizecalcstrategies.tests;

/**
 * Created by IntelliJ IDEA.
 * User: sergey
 * Date: 17/4/2005
 * Time: 17:06:25
 * To change this template use File | Settings | File Templates.
 */

import conexp.frontend.latticeeditor.BasicDrawParams;
import conexp.frontend.latticeeditor.DefaultDimensionCalcStrategy;
import conexp.frontend.latticeeditor.LatticePainterDrawParams;
import conexp.frontend.latticeeditor.edgesizecalcstrategies.ObjectFlowEdgeSizeCalcStrategy;
import conexp.frontend.latticeeditor.tests.DefaultDimensionCalcStrategyTest;

public class ObjectFlowEdgeSizeCalcStrategyTest extends DefaultDimensionCalcStrategyTest{

    protected DefaultDimensionCalcStrategy makeNotEqualInstance() {
        return new ObjectFlowEdgeSizeCalcStrategy(new LatticePainterDrawParams());
    }

    protected DefaultDimensionCalcStrategy makeEqualInstance() {
        return new ObjectFlowEdgeSizeCalcStrategy(BasicDrawParams.getInstance());
    }
}