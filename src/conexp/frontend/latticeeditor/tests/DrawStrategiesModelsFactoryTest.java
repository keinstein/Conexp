/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor.tests;

import conexp.frontend.latticeeditor.DrawParameters;
import junit.framework.TestCase;


public abstract class DrawStrategiesModelsFactoryTest extends TestCase {
    private conexp.frontend.latticeeditor.DrawStrategiesModelsFactory factory;
    private DrawParameters drawParams;

    protected abstract conexp.frontend.latticeeditor.DrawStrategiesModelsFactory makeFactory();

    protected void setUp() {
        drawParams = new conexp.frontend.latticeeditor.LatticePainterDrawParams();
        factory = makeFactory();
    }

    public void testCreators() {
        StrategyModelTest.testStrategyModel(factory.makeEdgeSizeStrategiesModel());
        StrategyModelTest.testStrategyModel(factory.makeNodeRadiusStrategiesModel());
        StrategyModelTest.testStrategyModel(factory.makeHighlightStrategiesModel());
    }


    protected DrawParameters getDrawParams() {
        return drawParams;
    }


}
