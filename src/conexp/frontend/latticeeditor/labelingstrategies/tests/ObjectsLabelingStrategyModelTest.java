/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.latticeeditor.labelingstrategies.tests;

import conexp.frontend.latticeeditor.DrawParameters;
import conexp.frontend.latticeeditor.labelingstrategies.ObjectsLabelingStrategyModel;
import conexp.util.gui.strategymodel.StrategyModel;

public class ObjectsLabelingStrategyModelTest extends conexp.frontend.latticeeditor.tests.StrategyModelTest {

    protected StrategyModel createStrategiesModel(DrawParameters opt) {
        return new ObjectsLabelingStrategyModel();
    }
}
