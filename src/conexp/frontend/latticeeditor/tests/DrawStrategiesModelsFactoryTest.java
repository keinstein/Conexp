package conexp.frontend.latticeeditor.tests;

import conexp.frontend.latticeeditor.DrawParameters;
import junit.framework.TestCase;

/**
 * Insert the type's description here.
 * Creation date: (24.01.01 0:36:00)
 * @author
 */
public abstract class DrawStrategiesModelsFactoryTest extends TestCase {
    protected conexp.frontend.latticeeditor.ModelsFactory factory;
    protected DrawParameters drawParams;

    protected abstract conexp.frontend.latticeeditor.ModelsFactory makeFactory();

    /**
     * Insert the method's description here.
     * Creation date: (24.01.01 0:38:24)
     */
    protected void setUp() {
        drawParams = new conexp.frontend.latticeeditor.LatticePainterDrawParams();
        factory = makeFactory();
    }

    /**
     * Insert the method's description here.
     * Creation date: (24.01.01 0:40:49)
     */
    public void testCreators() {
        testStrategyModel(factory.makeEdgeSizeStrategiesModel());
        testStrategyModel(factory.makeNodeRadiusStrategiesModel());
        testStrategyModel(factory.makeHighlightStrategiesModel());
    }


    /**
     * Insert the method's description here.
     * Creation date: (16.06.01 19:28:08)
     * @return conexp.frontend.latticeeditor.LatticePainterDrawParams
     */
    protected DrawParameters getDrawParams() {
        return drawParams;
    }


    /**
     * Insert the method's description here.
     * Creation date: (16.06.01 19:45:15)
     * @param model conexp.util.gui.strategymodel.StrategyModel
     */
    protected void testStrategiesExistense(conexp.util.gui.strategymodel.StrategyModel model) {
        for (int i = model.getStrategiesCount(); --i >= 0;) {
            assertNotNull("Strategy can't be null", model.getStrategy(i));
        }
    }


    /**
     * Insert the method's description here.
     * Creation date: (16.06.01 19:30:26)
     * @param model conexp.util.gui.strategymodel.StrategyModel
     */
    protected void testStrategyModel(conexp.util.gui.strategymodel.StrategyModel model) {
        assertNotNull(model);
        testStrategiesExistense(model);
        testStrategyUniquieness(model);
    }


    /**
     * Insert the method's description here.
     * Creation date: (16.06.01 19:45:55)
     * @param model conexp.util.gui.strategymodel.StrategyModel
     */
    public void testStrategyUniquieness(conexp.util.gui.strategymodel.StrategyModel model) {
        java.util.HashSet set = new java.util.HashSet();
        String[] strDesc = model.getStrategyDescription();
        assertNotNull("Strategies should have descriptions", strDesc);
        assertEquals("Count of strategies should be equal count of descriptions", model.getStrategiesCount(), strDesc.length);
        for (int i = strDesc.length; --i >= 0;) {
            assertNotNull("Description shouldn't be null", strDesc[i]);
            assertTrue("Description shouln't be empty", !"".equals(strDesc[i].trim()));
            assertTrue(set.add(strDesc[i]));
        }
    }
}