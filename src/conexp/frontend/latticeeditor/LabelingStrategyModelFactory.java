/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor;

import conexp.util.gui.strategymodel.StrategyModel;

public interface LabelingStrategyModelFactory {
    StrategyModel makeAttributeLabelingStrategiesModel();

    StrategyModel makeObjectsLabelingStrategiesModel();
}
