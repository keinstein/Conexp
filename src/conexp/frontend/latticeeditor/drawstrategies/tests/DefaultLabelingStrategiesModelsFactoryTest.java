/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/

package conexp.frontend.latticeeditor.drawstrategies.tests;

import conexp.frontend.latticeeditor.LabelingStrategyModelFactory;
import conexp.frontend.latticeeditor.drawstrategies.DefaultLabelingStrategiesFactory;
import conexp.frontend.latticeeditor.tests.LabelingStrategiesModelsFactoryTest;
import junit.framework.Test;
import junit.framework.TestSuite;

public class DefaultLabelingStrategiesModelsFactoryTest extends LabelingStrategiesModelsFactoryTest {
    protected LabelingStrategyModelFactory makeFactory() {
        return new DefaultLabelingStrategiesFactory(getDrawParams());
    }

    public static Test suite() {
        return new TestSuite(DefaultLabelingStrategiesModelsFactoryTest.class);
    }
}
