/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.core.layout.chaindecomposition.tests;

import conexp.util.gui.strategymodel.StrategyModel;
import conexp.util.gui.strategymodel.tests.StrategyModelBaseTest;
import junit.framework.TestSuite;

public class ChainDecompositionStrategyModelTest extends StrategyModelBaseTest {
    public static junit.framework.Test suite() {
        return new TestSuite(ChainDecompositionStrategyModelTest.class);
    }

    /**
     * Insert the method's description here.
     * Creation date: (30.03.01 0:59:29)
     * @return conexp.frontend.latticeeditor.StrategyModel
     */
    protected StrategyModel createStrategyModel() {
        return new conexp.core.layout.chaindecomposition.ChainDecompositionStrategyModel();
    }
}
