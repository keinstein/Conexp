/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.latticeeditor.drawstrategies;

import conexp.frontend.latticeeditor.DrawParameters;
import conexp.frontend.latticeeditor.LabelingStrategyModelFactory;
import conexp.util.gui.strategymodel.StrategyModel;

public class DefaultLabelingStrategiesFactory implements LabelingStrategyModelFactory {
    DrawParameters drawParams;

    public DefaultLabelingStrategiesFactory(DrawParameters drawParams) {
        this.drawParams = drawParams;
    }

    public StrategyModel makeAttributeLabelingStrategiesModel() {
        return new conexp.frontend.latticeeditor.labelingstrategies.AttributesLabelingStrategyModel(drawParams);
    }

    public StrategyModel makeObjectsLabelingStrategiesModel() {
        return new conexp.frontend.latticeeditor.labelingstrategies.ObjectsLabelingStrategyModel(drawParams);
    }
}
