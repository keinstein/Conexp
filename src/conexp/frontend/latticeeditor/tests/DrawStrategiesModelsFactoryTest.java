/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.latticeeditor.tests;

import conexp.frontend.latticeeditor.DrawParameters;
import junit.framework.TestCase;


public abstract class DrawStrategiesModelsFactoryTest extends TestCase {
    protected conexp.frontend.latticeeditor.ModelsFactory factory;
    protected DrawParameters drawParams;

    protected abstract conexp.frontend.latticeeditor.ModelsFactory makeFactory();

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
