/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor.tests;

import conexp.frontend.latticeeditor.DrawParameters;
import conexp.frontend.latticeeditor.LatticePainterDrawParams;
import conexp.util.gui.strategymodel.StrategyModel;
import conexp.util.gui.strategymodel.tests.StrategyModelBaseTest;

public abstract class StrategyModelTest extends StrategyModelBaseTest {

    protected abstract StrategyModel createStrategiesModel(DrawParameters opt);

    protected StrategyModel createStrategyModel() {
        return createStrategiesModel(new LatticePainterDrawParams());
    }

}
