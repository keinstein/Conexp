package conexp.frontend.latticeeditor.edgesizecalcstrategies.tests;

import conexp.frontend.latticeeditor.DrawParameters;
import conexp.frontend.latticeeditor.edgesizecalcstrategies.EdgeSizeStrategyModel;
import conexp.util.gui.strategymodel.StrategyModel;
import junit.framework.Test;
import junit.framework.TestSuite;

public class EdgeSizeStrategyModelTest extends conexp.frontend.latticeeditor.tests.StrategyModelTest {
    public static Test suite() {
        return new TestSuite(EdgeSizeStrategyModelTest.class);
    }

    /**
     * createStrategiesModel method comment.
     */
    protected StrategyModel createStrategiesModel(DrawParameters opt) {
        return new EdgeSizeStrategyModel(opt);
    }
}