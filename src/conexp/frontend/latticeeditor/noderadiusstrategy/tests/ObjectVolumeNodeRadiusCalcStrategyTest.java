package conexp.frontend.latticeeditor.noderadiusstrategy.tests;

/**
 * Created by IntelliJ IDEA.
 * User: sergey
 * Date: 17/4/2005
 * Time: 17:02:34
 * To change this template use File | Settings | File Templates.
 */

import conexp.frontend.latticeeditor.BasicDrawParams;
import conexp.frontend.latticeeditor.DefaultDimensionCalcStrategy;
import conexp.frontend.latticeeditor.LatticePainterDrawParams;
import conexp.frontend.latticeeditor.noderadiusstrategy.ObjectVolumeNodeRadiusCalcStrategy;
import conexp.frontend.latticeeditor.tests.DefaultDimensionCalcStrategyTest;

public class ObjectVolumeNodeRadiusCalcStrategyTest extends DefaultDimensionCalcStrategyTest {
    protected DefaultDimensionCalcStrategy makeNotEqualInstance() {
        return new ObjectVolumeNodeRadiusCalcStrategy(BasicDrawParams.getInstance());
    }

    protected DefaultDimensionCalcStrategy makeEqualInstance() {
        return new ObjectVolumeNodeRadiusCalcStrategy(new LatticePainterDrawParams());
    }

}