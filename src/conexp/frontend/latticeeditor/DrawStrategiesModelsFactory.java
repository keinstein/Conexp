package conexp.frontend.latticeeditor;

import conexp.util.gui.strategymodel.StrategyModel;

public interface DrawStrategiesModelsFactory{
    StrategyModel makeEdgeSizeStrategiesModel();

    StrategyModel makeHighlightStrategiesModel();

    StrategyModel makeLayoutStrategiesModel();

    StrategyModel makeNodeRadiusStrategiesModel();
}