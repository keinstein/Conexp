package conexp.frontend.latticeeditor.noderadiusstrategy.tests;

/**
 * User: sergey
 * Date: 18/4/2005
 * Time: 0:22:28
 */

import conexp.frontend.latticeeditor.BasicDrawParams;
import conexp.frontend.latticeeditor.DefaultDimensionCalcStrategy;
import conexp.frontend.latticeeditor.LatticePainterDrawParams;
import conexp.frontend.latticeeditor.noderadiusstrategy.OwnObjectsVolumeNodeRadiusCalcStrategy;
import conexp.frontend.latticeeditor.tests.DefaultDimensionCalcStrategyTest;

public class OwnObjectsVolumeNodeRadiusCalcStrategyTest extends DefaultDimensionCalcStrategyTest {
    protected DefaultDimensionCalcStrategy makeNotEqualInstance() {
        return new OwnObjectsVolumeNodeRadiusCalcStrategy(new LatticePainterDrawParams());
    }

    protected DefaultDimensionCalcStrategy makeEqualInstance() {
        return new OwnObjectsVolumeNodeRadiusCalcStrategy(BasicDrawParams.getInstance());
    }
}