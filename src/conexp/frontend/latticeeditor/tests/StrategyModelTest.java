/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.latticeeditor.tests;

import conexp.frontend.latticeeditor.DrawParameters;
import conexp.frontend.latticeeditor.LatticePainterDrawParams;
import conexp.util.gui.strategymodel.StrategyModel;

import java.util.Set;

public abstract class StrategyModelTest extends junit.framework.TestCase {
    protected StrategyModel model;
    DrawParameters opt;


    protected abstract StrategyModel createStrategiesModel(DrawParameters opt);

    protected void setUp() {
        opt = new LatticePainterDrawParams();
        model = createStrategiesModel(opt);
    }

    public void testMakeStrategy() {
        for (int i = model.getStrategiesCount(); --i >= 0;) {
            assertNotNull("Strategy can't be null", model.getStrategy(i));
        }

    }

    public void testStrategyDescription() {
        String[] strDesc = model.getStrategyDescription();
        assertNotNull("Strategies should have descriptions", strDesc);
        assertEquals("Count of strategies should be equal count of descriptions", model.getStrategiesCount(), strDesc.length);
        Set set = new java.util.HashSet();

        for (int i = strDesc.length; --i >= 0;) {
            assertNotNull("Description shouldn't be null", strDesc[i]);
            assertTrue("Description shouln't be empty", !"".equals(strDesc[i].trim()));
            assertTrue(set.add(strDesc[i]));
        }
    }

    public void testGetStrategyNameAndFindStrategyIndex() {
        int strategiesCount = model.getStrategiesCount();
        assertTrue("Tested strategy model shouldn't be empty", strategiesCount > 0);
        for (int i = 0; i < strategiesCount; i++) {
            String strategyName = model.getStrategyName(i);
            int strategyIndex = model.findStrategyIndex(strategyName);
            assertEquals(i, strategyIndex);
        }
    }
}
