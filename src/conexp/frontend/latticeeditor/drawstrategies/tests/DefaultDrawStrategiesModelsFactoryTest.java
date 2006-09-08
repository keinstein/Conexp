/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor.drawstrategies.tests;

import conexp.frontend.latticeeditor.DrawStrategiesModelsFactory;
import conexp.frontend.latticeeditor.drawstrategies.DefaultDrawStrategiesModelsFactory;
import conexp.frontend.latticeeditor.tests.DrawStrategiesModelsFactoryTest;


public class DefaultDrawStrategiesModelsFactoryTest extends DrawStrategiesModelsFactoryTest {
    protected DrawStrategiesModelsFactory makeFactory() {
        return new DefaultDrawStrategiesModelsFactory(getDrawParams());
    }
}
