/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor.edgesizecalcstrategies.tests;

import conexp.frontend.latticeeditor.DrawParameters;
import conexp.frontend.latticeeditor.edgesizecalcstrategies.EdgeSizeStrategyModel;
import conexp.frontend.latticeeditor.tests.StrategyModelTest;
import conexp.util.gui.strategymodel.StrategyModel;

public class EdgeSizeStrategyModelTest extends StrategyModelTest {

    protected StrategyModel createStrategiesModel(DrawParameters opt) {
        return new EdgeSizeStrategyModel(opt);
    }
}
