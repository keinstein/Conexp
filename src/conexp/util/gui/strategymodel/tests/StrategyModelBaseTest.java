package conexp.util.gui.strategymodel.tests;

import conexp.util.gui.strategymodel.StrategyModel;

/**
 * Created by IntelliJ IDEA.
 * User: sergey
 * Date: 6/4/2005
 * Time: 15:37:28
 * To change this template use File | Settings | File Templates.
 */
public abstract class StrategyModelBaseTest extends junit.framework.TestCase {
    protected StrategyModel model;

    public void testModel() {
        testStrategyModel(model);
    }

    public static void testGetStrategyNameAndFindIndex(final StrategyModel model) {
        int strategiesCount = model.getStrategiesCount();
        assertTrue("Tested strategy model shouldn't be empty", strategiesCount > 0);
        for (int i = 0; i < strategiesCount; i++) {
            String strategyName = model.getStrategyName(i);
            int strategyIndex = model.findStrategyIndex(strategyName);
            assertEquals(i, strategyIndex);
        }
    }

    public static void testStrategiesExistense(StrategyModel model) {
        for (int i = model.getStrategiesCount(); --i >= 0;) {
            assertNotNull("Strategy can't be null", model.getStrategy(i));
        }
    }

    public static void testStrategyModel(StrategyModel model) {
        assertNotNull(model);
        testStrategiesExistense(model);
        testStrategyUniquieness(model);
        testGetStrategyNameAndFindIndex(model);
    }

    public static void testStrategyUniquieness(StrategyModel model) {
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

    protected void setUp() {
        model = createStrategyModel();
    }

    protected abstract StrategyModel createStrategyModel();
}
