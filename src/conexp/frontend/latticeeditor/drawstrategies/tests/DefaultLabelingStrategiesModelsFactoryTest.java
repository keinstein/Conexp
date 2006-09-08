/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor.drawstrategies.tests;

import conexp.frontend.latticeeditor.LabelingStrategyModelFactory;
import conexp.frontend.latticeeditor.drawstrategies.DefaultLabelingStrategiesFactory;
import conexp.frontend.latticeeditor.tests.LabelingStrategiesModelsFactoryTest;

public class DefaultLabelingStrategiesModelsFactoryTest extends LabelingStrategiesModelsFactoryTest {
    protected LabelingStrategyModelFactory makeFactory() {
        return new DefaultLabelingStrategiesFactory();
    }
}
