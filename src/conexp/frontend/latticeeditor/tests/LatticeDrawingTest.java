/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.latticeeditor.tests;

import conexp.core.Lattice;
import conexp.core.layoutengines.SimpleLayoutEngine;
import conexp.core.tests.SetBuilder;
import conexp.frontend.latticeeditor.ConceptSetDrawing;
import conexp.frontend.latticeeditor.LatticeDrawing;
import conexp.frontend.latticeeditor.labelingstrategies.LabelingStrategiesKeys;


public class LatticeDrawingTest extends ConceptSetDrawingTest {
    private LatticeDrawing drawing;

    protected ConceptSetDrawing getDrawing(){
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

    public void testNeedUpdateCollision() throws Exception{
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


    public void testMakeCopy() throws Exception {
        LatticeDrawing other = drawing.makeSetupCopy();
        assertNotNull(other);
        assertNotSame(drawing, other);
        assertEquals(drawing.getOptions(), other.getOptions());
    }


    
}
