/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.latticeeditor.labelingstrategies.tests;

import conexp.core.Lattice;
import conexp.core.tests.SetBuilder;
import conexp.frontend.latticeeditor.LatticeDrawing;
import conexp.frontend.latticeeditor.labelingstrategies.AllObjectsLabelingStrategy;
import conexp.frontend.latticeeditor.labelingstrategies.GenericLabelingStrategy;
import conexp.frontend.latticeeditor.labelingstrategies.LabelingStrategiesKeys;
import conexp.frontend.latticeeditor.queries.ConceptNodeQuery;
import conexp.frontend.latticeeditor.queries.ConceptNodeQueryFactory;
import junit.framework.Test;
import junit.framework.TestSuite;


public class AllObjectsLabelingStrategyTest extends GenericLabelingStrategyTest {
    protected ConceptNodeQuery makeAcceptable() {
        return ConceptNodeQueryFactory.makeWithOwnObjects();
    }

    /**
     * makeStrategy method comment.
     */
    protected GenericLabelingStrategy makeStrategy() {
        return new AllObjectsLabelingStrategy(new conexp.frontend.latticeeditor.LatticePainterDrawParams());
    }

    /**
     * Insert the method's description here.
     * Creation date: (25.12.00 20:55:42)
     * @return junit.framework.Test
     */
    public static Test suite() {
        return new TestSuite(AllObjectsLabelingStrategyTest.class);
    }

    public void testAdditionOfAttributesAndCleanup() {
        Lattice lattice = SetBuilder.makeLatticeWithContext(new int[][]{{0, 1},
                                                                        {1, 0}});
        LatticeDrawing drawing = new LatticeDrawing();
        drawing.setLattice(lattice);
        assertTrue(drawing.setObjectLabelingStrategyKey(LabelingStrategiesKeys.NO_OBJECTS_LABELS_STRATEGY));
        assertEquals(false, drawing.hasLabelsForObjects());
        GenericLabelingStrategy alObjectsLabelingStrategy = makeStrategy();

        alObjectsLabelingStrategy.setContext(lattice.getContext());
        alObjectsLabelingStrategy.init(drawing);
        assertEquals(true, drawing.hasLabelsForObjects());
        alObjectsLabelingStrategy.shutdown(drawing);
        assertEquals(false, drawing.hasLabelsForObjects());

    }
}
