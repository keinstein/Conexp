package conexp.frontend.latticeeditor.labelingstrategies.tests;

import conexp.frontend.latticeeditor.DrawParameters;
import conexp.frontend.latticeeditor.labelingstrategies.AttributesLabelingStrategyModel;
import conexp.util.gui.strategymodel.StrategyModel;
import junit.framework.Test;
import junit.framework.TestSuite;

public class AttributesLabelingStrategyModelTest extends conexp.frontend.latticeeditor.tests.StrategyModelTest {
    public static Test suite() {
        return new TestSuite(AttributesLabelingStrategyModelTest.class);
    }

    /**
     * createStrategiesModel method comment.
     */
    protected StrategyModel createStrategiesModel(DrawParameters opt) {
        return new AttributesLabelingStrategyModel(opt);
    }
}