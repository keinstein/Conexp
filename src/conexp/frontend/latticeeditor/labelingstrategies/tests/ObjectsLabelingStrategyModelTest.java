/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.latticeeditor.labelingstrategies.tests;

import conexp.frontend.latticeeditor.DrawParameters;
import conexp.frontend.latticeeditor.labelingstrategies.ObjectsLabelingStrategyModel;
import conexp.util.gui.strategymodel.StrategyModel;
import junit.framework.Test;
import junit.framework.TestSuite;

public class ObjectsLabelingStrategyModelTest extends conexp.frontend.latticeeditor.tests.StrategyModelTest {
    public static Test suite() {
        return new TestSuite(ObjectsLabelingStrategyModelTest.class);
    }

    /**
     * createStrategiesModel method comment.
     */
    protected StrategyModel createStrategiesModel(DrawParameters opt) {
        return new ObjectsLabelingStrategyModel(opt);
    }
}
