/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.latticeeditor.tests;

import conexp.frontend.latticeeditor.DrawParameters;
import conexp.frontend.latticeeditor.LabelingStrategyModelFactory;
import junit.framework.TestCase;

public abstract class LabelingStrategiesModelsFactoryTest extends TestCase{
    protected conexp.frontend.latticeeditor.LabelingStrategyModelFactory factory;
    protected DrawParameters drawParams;

    protected abstract LabelingStrategyModelFactory makeFactory();

    protected void setUp() {
        drawParams = new conexp.frontend.latticeeditor.LatticePainterDrawParams();
        factory = makeFactory();
    }

    public void testCreators() {
        StrategyModelTest.testStrategyModel(factory.makeAttributeLabelingStrategiesModel());
        StrategyModelTest.testStrategyModel(factory.makeObjectsLabelingStrategiesModel());
    }


    protected DrawParameters getDrawParams() {
        return drawParams;
    }

}
