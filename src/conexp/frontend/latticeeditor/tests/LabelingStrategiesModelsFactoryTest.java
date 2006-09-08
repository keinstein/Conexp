/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor.tests;

import conexp.core.layout.LayoutParameters;
import conexp.frontend.latticeeditor.LabelingStrategyModelFactory;
import junit.framework.TestCase;

public abstract class LabelingStrategiesModelsFactoryTest extends TestCase {
    private conexp.frontend.latticeeditor.LabelingStrategyModelFactory factory;
    private LayoutParameters drawParams;

    protected abstract LabelingStrategyModelFactory makeFactory();

    protected void setUp() {
        drawParams = new conexp.frontend.latticeeditor.LatticePainterDrawParams();
        factory = makeFactory();
    }

    public void testCreators() {
        StrategyModelTest.testStrategyModel(factory.makeAttributeLabelingStrategiesModel());
        StrategyModelTest.testStrategyModel(factory.makeObjectsLabelingStrategiesModel());
    }


    protected LayoutParameters getDrawParams() {
        return drawParams;
    }

}
