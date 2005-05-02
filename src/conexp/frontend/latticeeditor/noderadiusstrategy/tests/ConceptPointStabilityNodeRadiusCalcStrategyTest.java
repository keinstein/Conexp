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
import conexp.frontend.latticeeditor.noderadiusstrategy.ConceptPointStabilityNodeRadiusCalcStrategy;
import conexp.frontend.latticeeditor.tests.DefaultDimensionCalcStrategyTest;

public class ConceptPointStabilityNodeRadiusCalcStrategyTest extends DefaultDimensionCalcStrategyTest {
    protected DefaultDimensionCalcStrategy makeNotEqualInstance() {
        return new ConceptPointStabilityNodeRadiusCalcStrategy(new LatticePainterDrawParams());
    }

    protected DefaultDimensionCalcStrategy makeEqualInstance() {
        return new ConceptPointStabilityNodeRadiusCalcStrategy(BasicDrawParams.getInstance());
    }
}