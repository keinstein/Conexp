/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.latticeeditor.tests;

import conexp.core.Lattice;
import conexp.core.layoutengines.SimpleLayoutEngine;
import conexp.core.tests.SetBuilder;
import conexp.frontend.latticeeditor.LatticeDrawing;
import conexp.frontend.latticeeditor.labelingstrategies.LabelingStrategiesKeys;
import junit.framework.TestCase;


public class LatticeDrawingTest extends TestCase {
    public void testLatticeDrawing() {
        LatticeDrawing drawing = new LatticeDrawing();
        drawing.setLattice(null);
    }

    public void testChangeOfAttributeAndObjectsModes() {
        LatticeDrawing drawing = new LatticeDrawing();
        drawing.setLattice(SetBuilder.makeLatticeWithContext(new int[][]{{1}}));
        drawing.setAttributeLabelingStrategyKey(LabelingStrategiesKeys.NO_ATTRIBS_LABELING_STRATEGY);
        assertEquals(false, drawing.hasLabelsForAttributes());
        drawing.setAttributeLabelingStrategyKey(LabelingStrategiesKeys.ALL_ATTRIBS_LABELING_STRATEGY_KEY);
        assertEquals(true, drawing.hasLabelsForAttributes());
    }

    public void testNeedUpdateCollision() {
        LatticeDrawing drawing = new LatticeDrawing();
        assertFalse(drawing.hasNeedUpdateCollisions());
        final Lattice lattice = SetBuilder.makeLatticeWithContext(new int[][]{{1,0},
                                                                                 {0,1}});
        drawing.setLattice(lattice);
        assertTrue(drawing.hasNeedUpdateCollisions());
        drawing.updateCollisions();
        assertFalse(drawing.hasNeedUpdateCollisions());
        drawing.setLayoutEngine(new SimpleLayoutEngine());
        drawing.layoutLattice();
        assertTrue(drawing.hasNeedUpdateCollisions());
        drawing.updateCollisions();
        assertFalse(drawing.hasNeedUpdateCollisions());
        drawing.getFigureForConcept(lattice.getZero()).moveBy(3, 3);
        assertTrue(drawing.hasNeedUpdateCollisions());
        drawing.updateCollisions();
        assertFalse(drawing.hasNeedUpdateCollisions());
    }



}
