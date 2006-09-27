/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor.tests;

import conexp.core.Lattice;
import conexp.core.LatticeElement;
import conexp.core.layoutengines.SimpleLayoutEngine;
import conexp.core.tests.SetBuilder;
import conexp.frontend.latticeeditor.ConceptSetDrawing;
import conexp.frontend.latticeeditor.LatticeDrawing;
import conexp.frontend.latticeeditor.figures.SimpleTextFigure;
import conexp.frontend.latticeeditor.labelingstrategies.LabelingStrategiesKeys;


public class LatticeDrawingTest extends ConceptSetDrawingTest {
    private LatticeDrawing drawing;

    protected ConceptSetDrawing getDrawing() {
        return drawing;
    }

    protected void setUp() {
        drawing = new LatticeDrawing();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        drawing = null;
    }

    public void testLatticeDrawing() {
        drawing.setLattice(null);
    }

    public void testChangeOfAttributeAndObjectsModes() {
        drawing.setLattice(SetBuilder.makeLatticeWithContext(new int[][]{{1}}));
        drawing.setAttributeLabelingStrategyKey(LabelingStrategiesKeys.NO_ATTRIBS_LABELING_STRATEGY);
        assertEquals(false, drawing.hasLabelsForAttributes());
        drawing.setAttributeLabelingStrategyKey(LabelingStrategiesKeys.ALL_ATTRIBS_LABELING_STRATEGY_KEY);
        assertEquals(true, drawing.hasLabelsForAttributes());
    }

    public void testNeedUpdateCollision() {
        assertFalse(drawing.hasNeedUpdateCollisions());
        final Lattice lattice = SetBuilder.makeLatticeWithContext(new int[][]{{1, 0},
                {0, 1}});
        drawing.setLattice(lattice);
        checkUpdateCycle(drawing);

        drawing.setLayoutEngine(new SimpleLayoutEngine());
        drawing.layoutLattice();
        checkUpdateCycle(drawing);
        //without this code it is not guaranted, that update collisions will lead to actual update
        //but we accept it, as update collisions is called from every paint method

        drawing.getFigureForConcept(lattice.getZero()).moveBy(3, 3);
        checkUpdateCycle(drawing);
    }

    public void testCollisionUpdateWhenCollisionDetectionIsDisabled() {
        assertFalse(drawing.hasNeedUpdateCollisions());
        assertTrue(drawing.isEmpty());
        drawing.getEditableDrawParameters().setShowCollisions(false);

        assertFalse("Change in update collision option should not lead to update for empty drawing",
                drawing.hasNeedUpdateCollisions());
        final Lattice lattice = SetBuilder.makeLatticeWithContext(new int[][]{{1, 0},
                {0, 1}});
        drawing.setLattice(lattice);
        assertFalse(drawing.hasNeedUpdateCollisions());
        drawing.setLayoutEngine(new SimpleLayoutEngine());
        drawing.layoutLattice();
        assertFalse(drawing.hasNeedUpdateCollisions());
        drawing.getEditableDrawParameters().setShowCollisions(true);
        assertTrue(drawing.hasNeedUpdateCollisions());
        checkUpdateCycle(drawing);
        assertFalse(drawing.hasNeedUpdateCollisions());
        drawing.getEditableDrawParameters().setShowCollisions(false);

        assertTrue("Change in update collision option should lead to update for non-empty drawing",
                drawing.hasNeedUpdateCollisions());
        checkUpdateCycle(drawing);
        drawing.getFigureForConcept(lattice.getZero()).moveBy(3, 3);
        assertFalse(drawing.hasNeedUpdateCollisions());
    }

    private static void checkUpdateCycle(LatticeDrawing drawing) {
        assertTrue(drawing.hasNeedUpdateCollisions());
        drawing.updateCollisions();
        while (drawing.isUpdatingCollisions()) {
            /*intentionally empty*/
        }
        assertFalse(drawing.hasNeedUpdateCollisions());
    }


    public void testMakeSetupCopy() {
        LatticeDrawing other = drawing.makeSetupCopy();
        assertNotNull(other);
        assertNotSame(drawing, other);
        assertTrue(drawing.getOptions().isEqual(other.getOptions()));
    }


    public void testHasLabelsForConcept() {
        final Lattice lattice = SetBuilder.makeLatticeWithContext(new int[][]{{1, 0},
                {0, 1}});
        drawing.setLattice(lattice);
//        drawing.layoutLattice();

        assertEquals(LabelingStrategiesKeys.NO_ATTRIBS_LABELING_STRATEGY,
                drawing.getAttributeLabelingStrategyKey());
        assertEquals(LabelingStrategiesKeys.NO_OBJECTS_LABELING_STRATEGY,
                drawing.getObjectLabelingStrategyKey());
        drawing.setObjectLabelingStrategyKey(LabelingStrategiesKeys.OBJECTS_COUNT_LABELING_STRATEGY);

        assertEquals(LabelingStrategiesKeys.OBJECTS_COUNT_LABELING_STRATEGY,
                drawing.getObjectLabelingStrategyKey());
        assertEquals("labelsForConcepts should be true", true, drawing.hasDownLabelsForConcepts());
        assertEquals("labelsForAttributes should be false", false, drawing.hasLabelsForAttributes());
        assertEquals("labelsForObjects should be false", false, drawing.hasLabelsForObjects());
        drawing.setAttributeLabelingStrategyKey(LabelingStrategiesKeys.
                ATTRIBS_MULTI_LABELING_STRATEGY_KEY);
        assertTrue("Expect that drawing has labels for concepts", drawing.hasUpLabelsForConcepts());

        drawing.setObjectLabelingStrategyKey(
                LabelingStrategiesKeys.NO_OBJECTS_LABELING_STRATEGY);
        assertFalse("Expect that drawing has no labels for objects", drawing.hasLabelsForObjects());
        assertFalse("There should be no down concept labels", drawing.hasDownLabelsForConcepts());
        assertTrue("Setting object labeling strategy should not affect concept labeling strategy",
                drawing.hasUpLabelsForConcepts());

    }


    public void testSetUpLabelForConceptAndClearingLabels() {
        final Lattice lattice = SetBuilder.makeLatticeWithContext(new int[][]{{1, 0},
                {0, 1}});
        drawing.setLattice(lattice);

        assertFalse(getDrawing().hasUpLabelsForConcepts());
        LatticeElement zero = drawing.getLattice().getZero();
        SimpleTextFigure figure =
                new SimpleTextFigure(drawing.makeConceptQueryForElement(zero),
                        "Test text");
        drawing.setUpLabelForConcept(zero,
                figure);
        assertTrue(drawing.hasUpLabelsForConcepts());
        assertSame(figure, drawing.getUpLabelForConcept(zero));
        drawing.clearUpLabelsForConcepts();
        assertFalse(drawing.hasUpLabelsForConcepts());
    }


    public void testLabelingStrategies() {

        /*
            There will be actually two types of lebelling strategies:
            upper semisphere and lower semisphere
            They can be divided on based on memory consumption on
            object/attribute/ concept related
            Actually they are mainly upper-concept-drawing or lower-concept-drawing
        */

    }


}
