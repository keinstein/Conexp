package conexp.frontend.latticeeditor.noderadiusstrategy.tests;

/**
 * Created by IntelliJ IDEA.
 * User: sergey
 * Date: 18/4/2005
 * Time: 0:22:28
 * To change this template use File | Settings | File Templates.
 */

import conexp.frontend.latticeeditor.BasicDrawParams;
import conexp.frontend.latticeeditor.DefaultDimensionCalcStrategy;
import conexp.frontend.latticeeditor.LatticePainterDrawParams;
import conexp.frontend.latticeeditor.noderadiusstrategy.ConceptIntegralStabilityNodeRadiusCalcStrategy;
import conexp.frontend.latticeeditor.tests.DefaultDimensionCalcStrategyTest;

public class ConceptIntegralStabilityNodeRadiusCalcStrategyTest extends DefaultDimensionCalcStrategyTest{
    protected DefaultDimensionCalcStrategy makeNotEqualInstance() {
        return new ConceptIntegralStabilityNodeRadiusCalcStrategy(new LatticePainterDrawParams());
    }

    protected DefaultDimensionCalcStrategy makeEqualInstance() {
        return new ConceptIntegralStabilityNodeRadiusCalcStrategy(BasicDrawParams.getInstance());
    }
}