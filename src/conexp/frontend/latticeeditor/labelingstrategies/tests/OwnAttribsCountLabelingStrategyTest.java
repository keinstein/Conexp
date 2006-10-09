package conexp.frontend.latticeeditor.labelingstrategies.tests;

import conexp.frontend.latticeeditor.queries.ConceptNodeQuery;
import conexp.frontend.latticeeditor.labelingstrategies.GenericLabelingStrategy;
import conexp.frontend.latticeeditor.labelingstrategies.LabelingStrategiesKeys;
import conexp.frontend.latticeeditor.labelingstrategies.OwnAttribsCountLabelingStrategy;
import conexp.frontend.latticeeditor.LatticeDrawing;
import conexp.core.Lattice;
import conexp.core.tests.SetBuilder;
import conexp.frontend.latticeeditor.queries.tests.ConceptNodeQueryFactory;


public class OwnAttribsCountLabelingStrategyTest extends GenericLabelingStrategyTest {
    protected ConceptNodeQuery makeAcceptable() {
        return ConceptNodeQueryFactory.makeWithOwnAttribs();
    }

    protected GenericLabelingStrategy makeStrategy() {
        return new OwnAttribsCountLabelingStrategy();
    }

    public void testAdditionOfConceptLabelsAndCleanup() {
        Lattice lattice = SetBuilder.makeLatticeWithContext(new int[][]{{0, 1},
                {1, 0}});
        LatticeDrawing drawing = new LatticeDrawing();
        drawing.setLattice(lattice);

        assertTrue("Expect that strategy will be set successfully",

                drawing.setAttributeLabelingStrategyKey(LabelingStrategiesKeys.NO_ATTRIBS_LABELING_STRATEGY));

        assertEquals("Drawing should not have any up labels for concepts",false, drawing.hasUpLabelsForConcepts());
        GenericLabelingStrategy ownAttrCountStrategy = makeStrategy();

        ownAttrCountStrategy.setContext(lattice.getContext());
        ownAttrCountStrategy.init(drawing, makeDrawParams());
        assertEquals("Drawing after initialization of strategy should have up labels",true, drawing.hasUpLabelsForConcepts());
        ownAttrCountStrategy.shutdown(drawing);
        assertEquals("Drawing after shutdown of strategy should not have any up labels",false, drawing.hasUpLabelsForConcepts());

    }
}
