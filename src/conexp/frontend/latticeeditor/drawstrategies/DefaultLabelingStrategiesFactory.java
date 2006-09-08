/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor.drawstrategies;

import conexp.frontend.latticeeditor.LabelingStrategyModelFactory;
import conexp.frontend.latticeeditor.labelingstrategies.AttributesLabelingStrategyModel;
import conexp.frontend.latticeeditor.labelingstrategies.ObjectsLabelingStrategyModel;
import conexp.util.gui.strategymodel.StrategyModel;

public class DefaultLabelingStrategiesFactory implements LabelingStrategyModelFactory {
    public DefaultLabelingStrategiesFactory() {
    }

    public StrategyModel makeAttributeLabelingStrategiesModel() {
        return new AttributesLabelingStrategyModel();
    }

    public StrategyModel makeObjectsLabelingStrategiesModel() {
        return new ObjectsLabelingStrategyModel();
    }
}
