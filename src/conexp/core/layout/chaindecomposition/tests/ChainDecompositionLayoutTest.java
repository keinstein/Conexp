/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.layout.chaindecomposition.tests;

import conexp.core.layout.GenericLayouter;
import conexp.core.layout.chaindecomposition.ChainDecompositionLayout;
import conexp.core.layout.tests.GenericLayouterTest;
import junit.framework.Test;
import junit.framework.TestSuite;
import util.testing.SimpleMockPropertyChangeListener;


public class ChainDecompositionLayoutTest extends GenericLayouterTest {
    protected boolean isTestImproveOnce() {
        return false;
    }

    /**
     * Insert the method's description here.
     * Creation date: (30.03.01 3:57:11)
     *
     * @return conexp.core.layout.GenericLayouter
     */
    protected GenericLayouter makeLayouter() {
        return new ChainDecompositionLayout();
    }

    /**
     * Insert the method's description here.
     * Creation date: (30.03.01 3:59:35)
     *
     * @return junit.framework.Test
     */
    public static Test suite() {
        return new TestSuite(ChainDecompositionLayoutTest.class);
    }

    /**
     * Insert the method's description here.
     * Creation date: (30.03.01 5:39:52)
     */
    public void testParamChange() {
        ChainDecompositionLayout layouter = (ChainDecompositionLayout) makeLayouter();
        SimpleMockPropertyChangeListener listener = new SimpleMockPropertyChangeListener(ChainDecompositionLayout.DECOMPOSITION_STRATEGY_EVENT);
        layouter.setPropertyChangeListener(listener);
        listener.setExpected(1);
        assertEquals(0, layouter.getDecompositionStrategyItem().getValue());
        layouter.getDecompositionStrategyItem().setValue(1);
        listener.verify();
        assertEquals(1, layouter.getDecompositionStrategyItem().getValue());

        listener.setExpectedPropertyName(ChainDecompositionLayout.CONCEPT_PLACEMENT_EVENT);
        listener.setExpected(1);
        assertEquals(0, layouter.getConceptPlacementStrategyItem().getValue());
        layouter.getConceptPlacementStrategyItem().setValue(1);
        listener.verify();
        assertEquals(1, layouter.getConceptPlacementStrategyItem().getValue());

    }
}
