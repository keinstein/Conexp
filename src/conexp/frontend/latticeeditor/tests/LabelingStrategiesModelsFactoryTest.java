package conexp.frontend.latticeeditor.tests;

import conexp.frontend.latticeeditor.DrawParameters;
import conexp.frontend.latticeeditor.LabelingStrategyModelFactory;
import junit.framework.TestCase;

public abstract class LabelingStrategiesModelsFactoryTest extends TestCase {
    protected conexp.frontend.latticeeditor.LabelingStrategyModelFactory factory;
    protected DrawParameters drawParams;

    protected abstract LabelingStrategyModelFactory makeFactory();

    protected void setUp() {
        drawParams = new conexp.frontend.latticeeditor.LatticePainterDrawParams();
        factory = makeFactory();
    }

    public void testCreators() {
        testStrategyModel(factory.makeAttributeLabelingStrategiesModel());
        testStrategyModel(factory.makeObjectsLabelingStrategiesModel());
    }


    protected DrawParameters getDrawParams() {
        return drawParams;
    }

    protected void testStrategiesExistense(conexp.util.gui.strategymodel.StrategyModel model) {
        for (int i = model.getStrategiesCount(); --i >= 0;) {
            assertNotNull("Strategy can't be null", model.getStrategy(i));
        }
    }

    protected void testStrategyModel(conexp.util.gui.strategymodel.StrategyModel model) {
        assertNotNull(model);
        testStrategiesExistense(model);
        testStrategyUniquieness(model);
    }

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