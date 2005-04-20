/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.components.tests;

import conexp.core.Context;
import conexp.core.FCAEngineRegistry;
import conexp.core.Lattice;
import conexp.core.LatticeElement;
import conexp.core.layoutengines.SimpleLayoutEngine;
import conexp.core.tests.SetBuilder;
import conexp.frontend.LatticeCalculator;
import conexp.frontend.components.LatticeComponent;
import conexp.frontend.latticeeditor.LatticeDrawing;
import junit.framework.TestCase;
import util.collection.CollectionFactory;
import util.testing.SimpleMockPropertyChangeListener;

import java.util.Set;

public class LatticeComponentTest extends TestCase {
    LatticeComponent latticeComponent;

    protected void setUp() {
        latticeComponent = new LatticeComponent(SetBuilder.makeContext(new int[][]{{0, 1, 1},
                                                                                   {1, 0, 0}}));
    }


    public void testLatticeCalculateLattice() {
        SimpleMockPropertyChangeListener listener = new SimpleMockPropertyChangeListener(LatticeCalculator.LATTICE_DRAWING_CHANGED);
        listener.setExpected(1);
        latticeComponent.addPropertyChangeListener(LatticeCalculator.LATTICE_DRAWING_CHANGED, listener);
        latticeComponent.calculateLattice();
        listener.verify();
    }

    public void testLatticeClearLattice() {
        SimpleMockPropertyChangeListener listener = new SimpleMockPropertyChangeListener(LatticeCalculator.LATTICE_CLEARED);
        listener.setExpected(1);
        latticeComponent.addPropertyChangeListener(LatticeCalculator.LATTICE_CLEARED, listener);
        latticeComponent.clearLattice();
        listener.verify();
    }

    public void testCalcLattice() {
        latticeComponent = new LatticeComponent(SetBuilder.makeContext(new int[][]{{1, 1, 1},
                                                                                   {0, 1, 1}}));

        latticeComponent.calculateLattice();
        assertTrue(latticeComponent.getLattice().isValid());
    }

    public void testCalcLattice2() {
        latticeComponent = new LatticeComponent(SetBuilder.makeContext(new int[][]{{1, 1, 1},
                                                                                   {0, 1, 1}}));
        latticeComponent.calculateLattice();
        assertTrue(latticeComponent.getLattice().isValid());
        assertTrue(!latticeComponent.getDrawing().isEmpty());
    }

    public void testCalcPartialLattice() {
        latticeComponent = new LatticeComponent(SetBuilder.makeContext(new int[][]{{0, 1, 1},
                                                                                   {1, 0, 1},
                                                                                   {1, 1, 0}}));
        latticeComponent.calculateLattice();
        assertTrue(latticeComponent.getLattice().isValid());
        assertTrue(!latticeComponent.getDrawing().isEmpty());
        latticeComponent.getAttributeMask().setSelected(0, false);
        latticeComponent.calculatePartialLattice();
        assertTrue(latticeComponent.getLattice().isValid());
        assertTrue(!latticeComponent.getDrawing().isEmpty());

    }

    public static void testCoordinatesAssignment() {

        Context cxt = FCAEngineRegistry.makeContext(3, 3);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (i != j) {
                    cxt.setRelationAt(i, j, true);
                }
            }
        }

        LatticeComponent component = new LatticeComponent(cxt);
        component.setLayoutEngine(new SimpleLayoutEngine());
        component.calculateAndLayoutLattice();
        final LatticeDrawing drawing = component.getDrawing();
        final Set distinctCoors = CollectionFactory.createDefaultSet();
        Lattice lattice = component.getLattice();
        lattice.forEach(new Lattice.LatticeElementVisitor() {
            public void visitNode(LatticeElement node) {
                distinctCoors.add(drawing.getFigureForConcept(node).getCenter());
            }
        });
        assertEquals(8, distinctCoors.size());
    }

    public void testMakeCopy() throws Exception {
        latticeComponent = new LatticeComponent(SetBuilder.makeContext(new int[][]{{0, 1, 1},
                                                                                   {1, 0, 1},
                                                                                   {1, 1, 0}}));
        latticeComponent.calculateLattice();

        LatticeComponent other = latticeComponent.makeCopy();
        assertEquals(latticeComponent.getAttributeMask(), other.getAttributeMask());
        assertEquals(latticeComponent.getObjectMask(), other.getObjectMask());
        assertSame(latticeComponent.getLattice().getContext(), other.getLattice().getContext());
        assertEquals(latticeComponent.getLattice(), other.getLattice());

//        assertEquals(latticeComponent.getDrawing(), other.getDrawing());
/*

        assertEquals(latticeComponent, other);


        other.getAttributeMask().setSelected(0, false);
        assertEquals(false, latticeComponent.equals(other));
        assertEquals(false, latticeComponent.getAttributeMask().equals(other.getAttributeMask()));
*/
    }
}
