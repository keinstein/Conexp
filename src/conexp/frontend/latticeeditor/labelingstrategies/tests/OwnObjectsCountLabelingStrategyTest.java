/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor.labelingstrategies.tests;

import conexp.core.Lattice;
import conexp.core.tests.SetBuilder;
import conexp.frontend.latticeeditor.LatticeDrawing;
import conexp.frontend.latticeeditor.labelingstrategies.GenericLabelingStrategy;
import conexp.frontend.latticeeditor.labelingstrategies.LabelingStrategiesKeys;
import conexp.frontend.latticeeditor.labelingstrategies.OwnObjectsCountLabelingStrategy;
import conexp.frontend.latticeeditor.queries.ConceptNodeQuery;
import conexp.frontend.latticeeditor.queries.tests.ConceptNodeQueryFactory;

public class OwnObjectsCountLabelingStrategyTest extends GenericLabelingStrategyTest {
    protected ConceptNodeQuery makeAcceptable() {
        return ConceptNodeQueryFactory.makeWithOwnObjects();
    }

    protected GenericLabelingStrategy makeStrategy() {
        return new OwnObjectsCountLabelingStrategy();
    }

    public void testAdditionOfConceptLabelsAndCleanup() {
        Lattice lattice = SetBuilder.makeLatticeWithContext(new int[][]{{0, 1},
                {1, 0}});
        LatticeDrawing drawing = new LatticeDrawing();
        drawing.setLattice(lattice);
        assertTrue(drawing.setObjectLabelingStrategyKey(LabelingStrategiesKeys.ALL_OBJECTS_LABELING_STRATEGY));
        assertEquals(false, drawing.hasDownLabelsForConcepts());
        GenericLabelingStrategy ownObjectCountStrategy = makeStrategy();

        ownObjectCountStrategy.setContext(lattice.getContext());
        ownObjectCountStrategy.init(drawing, makeDrawParams());
        assertEquals(true, drawing.hasDownLabelsForConcepts());
        ownObjectCountStrategy.shutdown(drawing);
        assertEquals(false, drawing.hasDownLabelsForConcepts());

    }
}
