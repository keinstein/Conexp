/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.latticeeditor.noderadiusstrategy.tests;

import conexp.frontend.latticeeditor.DrawParameters;
import conexp.frontend.latticeeditor.tests.StrategyModelTest;
import conexp.util.gui.strategymodel.StrategyModel;
import junit.framework.Test;
import junit.framework.TestSuite;

public class NodeRadiusStrategyModelTest extends StrategyModelTest {
    protected StrategyModel createStrategiesModel(DrawParameters opt) {
        return new conexp.frontend.latticeeditor.noderadiusstrategy.NodeRadiusStrategyModel(opt);
    }
}
