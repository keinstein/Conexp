package conexp.frontend.latticeeditor;

import conexp.util.gui.strategymodel.NonGrowingStrategyModel;

public abstract class AbstractDrawingStrategyModel extends NonGrowingStrategyModel {

    protected AbstractDrawingStrategyModel(DrawParameters drawParams) {
        createStrategies(drawParams);
    }

    protected abstract void createStrategies(DrawParameters drawparams);

    protected AbstractDrawingStrategyModel() {

    }
}