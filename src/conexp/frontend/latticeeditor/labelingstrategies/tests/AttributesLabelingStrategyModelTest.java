/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.latticeeditor.labelingstrategies.tests;

import conexp.frontend.latticeeditor.DrawParameters;
import conexp.frontend.latticeeditor.labelingstrategies.AttributesLabelingStrategyModel;
import conexp.util.gui.strategymodel.StrategyModel;

public class AttributesLabelingStrategyModelTest extends conexp.frontend.latticeeditor.tests.StrategyModelTest {

    protected StrategyModel createStrategiesModel(DrawParameters opt) {
        return new AttributesLabelingStrategyModel();
    }
}
