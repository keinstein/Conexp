/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor.labelingstrategies.tests;

import conexp.frontend.latticeeditor.labelingstrategies.ObjectsLabelingStrategyModel;
import conexp.util.gui.strategymodel.StrategyModel;
import conexp.util.gui.strategymodel.tests.StrategyModelBaseTest;

public class ObjectsLabelingStrategyModelTest extends StrategyModelBaseTest {

    protected StrategyModel createStrategyModel() {
        return new ObjectsLabelingStrategyModel();
    }
}
