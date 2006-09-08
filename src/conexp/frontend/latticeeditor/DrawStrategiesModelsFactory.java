/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor;

import conexp.util.gui.strategymodel.StrategyModel;

public interface DrawStrategiesModelsFactory {
    StrategyModel makeEdgeSizeStrategiesModel();

    StrategyModel makeHighlightStrategiesModel();

    StrategyModel makeLayoutStrategiesModel();

    StrategyModel makeNodeRadiusStrategiesModel();

    DrawStrategiesModelsFactory makeCopy();
}
