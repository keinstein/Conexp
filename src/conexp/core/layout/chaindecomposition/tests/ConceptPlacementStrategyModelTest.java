package conexp.core.layout.chaindecomposition.tests;

import conexp.frontend.latticeeditor.DrawParameters;
import conexp.util.gui.strategymodel.StrategyModel;
import junit.framework.TestSuite;

public class ConceptPlacementStrategyModelTest extends conexp.frontend.latticeeditor.tests.StrategyModelTest {
    public static junit.framework.Test suite() {
        return new TestSuite(ChainDecompositionStrategyModelTest.class);
    }

    /**
     * Insert the method's description here.
     * Creation date: (30.03.01 0:59:29)
     * @return conexp.frontend.latticeeditor.StrategyModel
     */
    protected StrategyModel createStrategiesModel(DrawParameters opt) {
        return new conexp.core.layout.chaindecomposition.ConceptPlacementStrategyModel();
    }
}