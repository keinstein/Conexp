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

    public void testNeedUpdateCollision() throws Exception{
        LatticeDrawing drawing = new LatticeDrawing();
        assertFalse(drawing.hasNeedUpdateCollisions());
        final Lattice lattice = SetBuilder.makeLatticeWithContext(new int[][]{{1,0},
                                                                                 {0,1}});
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

    private void checkUpdateCycle(LatticeDrawing drawing) {
        assertTrue(drawing.hasNeedUpdateCollisions());
        drawing.updateCollisions();
        while(drawing.isUpdatingCollisions()){}
        assertFalse(drawing.hasNeedUpdateCollisions());
    }


}
