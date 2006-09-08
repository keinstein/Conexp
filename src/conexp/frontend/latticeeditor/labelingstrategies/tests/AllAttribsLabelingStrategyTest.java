/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor.labelingstrategies.tests;

import conexp.core.Lattice;
import conexp.core.tests.SetBuilder;
import conexp.frontend.latticeeditor.LatticeDrawing;
import conexp.frontend.latticeeditor.labelingstrategies.AllAttribsLabelingStrategy;
import conexp.frontend.latticeeditor.labelingstrategies.GenericLabelingStrategy;
import conexp.frontend.latticeeditor.labelingstrategies.LabelingStrategiesKeys;
import conexp.frontend.latticeeditor.queries.ConceptNodeQuery;
import conexp.frontend.latticeeditor.queries.tests.ConceptNodeQueryFactory;

public class AllAttribsLabelingStrategyTest extends GenericLabelingStrategyTest {
    protected ConceptNodeQuery makeAcceptable() {
        return ConceptNodeQueryFactory.makeWithOwnAttribs();
    }

    protected GenericLabelingStrategy makeStrategy() {
        return new AllAttribsLabelingStrategy();
    }

    public void testAdditionOfAttributesAndCleanup() {
        Lattice lattice = SetBuilder.makeLatticeWithContext(new int[][]{{0, 1},
                {1, 0}});
        LatticeDrawing drawing = new LatticeDrawing();
        drawing.setLattice(lattice);
        assertTrue(drawing.setAttributeLabelingStrategyKey(LabelingStrategiesKeys.NO_ATTRIBS_LABELING_STRATEGY));
        assertEquals(false, drawing.hasLabelsForAttributes());
        GenericLabelingStrategy allAttribsLabelingStrategy = makeStrategy();

        allAttribsLabelingStrategy.setContext(lattice.getContext());
        allAttribsLabelingStrategy.init(drawing, makeDrawParams());
        assertEquals(true, drawing.hasLabelsForAttributes());
        allAttribsLabelingStrategy.shutdown(drawing);
        assertEquals(false, drawing.hasLabelsForAttributes());

    }

}
