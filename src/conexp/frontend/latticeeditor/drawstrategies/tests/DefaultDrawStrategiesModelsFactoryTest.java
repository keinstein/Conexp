/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.latticeeditor.drawstrategies.tests;

import junit.framework.Test;
import junit.framework.TestSuite;


public class DefaultDrawStrategiesModelsFactoryTest extends conexp.frontend.latticeeditor.tests.DrawStrategiesModelsFactoryTest {
    protected conexp.frontend.latticeeditor.ModelsFactory makeFactory() {
        return new conexp.frontend.latticeeditor.drawstrategies.DefaultDrawStrategiesModelsFactory(getDrawParams());
    }

    public static Test suite() {
        return new TestSuite(DefaultDrawStrategiesModelsFactoryTest.class);
    }
}
