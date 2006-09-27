/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.frontend.latticeeditor.labelingstrategies.tests;

import conexp.core.Lattice;
import conexp.core.tests.SetBuilder;
import conexp.frontend.latticeeditor.LatticeDrawing;
import conexp.frontend.latticeeditor.labelingstrategies.AllObjectsMultiLineLabelingStrategy;
import conexp.frontend.latticeeditor.labelingstrategies.GenericLabelingStrategy;
import conexp.frontend.latticeeditor.labelingstrategies.LabelingStrategiesKeys;
import conexp.frontend.latticeeditor.queries.ConceptNodeQuery;
import conexp.frontend.latticeeditor.queries.tests.ConceptNodeQueryFactory;

public class AllObjectsMultiLineLabelingStrategyTest extends GenericLabelingStrategyTest {
    protected ConceptNodeQuery makeAcceptable() {
        return ConceptNodeQueryFactory.makeWithOwnObjects();
    }

    protected GenericLabelingStrategy makeStrategy() {
        return new AllObjectsMultiLineLabelingStrategy();
    }

    public void testAdditionOfAttributesAndCleanup() {
        Lattice lattice = SetBuilder.makeLatticeWithContext(new int[][]{{0, 1},
                {1, 0}});
        LatticeDrawing drawing = new LatticeDrawing();
        drawing.setLattice(lattice);
        assertTrue(drawing.setObjectLabelingStrategyKey(LabelingStrategiesKeys.NO_OBJECTS_LABELING_STRATEGY));
        assertEquals(false, drawing.hasDownLabelsForConcepts());
        GenericLabelingStrategy alObjectsLabelingStrategy = makeStrategy();
        alObjectsLabelingStrategy.setContext(lattice.getContext());
        alObjectsLabelingStrategy.init(drawing, makeDrawParams());
        assertEquals(true, drawing.hasDownLabelsForConcepts());
        alObjectsLabelingStrategy.shutdown(drawing);
        assertEquals(false, drawing.hasDownLabelsForConcepts());

    }

    public void testGetLabelLocationAngleInRadians() {
        assertEquals(0.5 * Math.PI,
                getNativeStrategy().getLabelLocationAngleInRadians(),
                0.0001);
    }

    private AllObjectsMultiLineLabelingStrategy getNativeStrategy() {
        return ((AllObjectsMultiLineLabelingStrategy) labelStrategy);
    }
}
