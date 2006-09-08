/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.components.tests;

import conexp.core.Lattice;
import conexp.core.LatticeElement;
import conexp.frontend.LatticeCalculator;
import conexp.frontend.components.LatticeComponent;
import conexp.frontend.components.LatticeSupplier;
import conexp.frontend.latticeeditor.LatticeDrawing;
import conexp.frontend.latticeeditor.figures.AbstractConceptCorrespondingFigure;
import junit.framework.TestCase;
import util.collection.CollectionFactory;
import util.testing.SimpleMockPropertyChangeListener;

import java.util.Set;

public class LatticeComponentTest extends TestCase {
    private LatticeComponent latticeComponent;

    protected void setUp() {
        int[][] relation = new int[][]{{0, 1, 1},
                {1, 0, 0}};
        latticeComponent = ComponentsObjectMother.makeLatticeComponent(relation);
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
        latticeComponent = ComponentsObjectMother.makeLatticeComponent(new int[][]{{1, 1, 1},
                {0, 1, 1}});

        latticeComponent.calculateLattice();
        assertTrue(latticeComponent.getLattice().isValid());
    }

    public void testCalcLattice2() {
        latticeComponent = ComponentsObjectMother.makeLatticeComponent(new int[][]{{1, 1, 1},
                {0, 1, 1}});
        latticeComponent.calculateLattice();
        assertTrue(latticeComponent.getLattice().isValid());
        assertTrue(!latticeComponent.getDrawing().isEmpty());
    }

    public void testCalcPartialLattice() {
        latticeComponent = ComponentsObjectMother.makeLatticeComponent(new int[][]{{0, 1, 1},
                {1, 0, 1},
                {1, 1, 0}});
        latticeComponent.calculateLattice();
        assertTrue(latticeComponent.getLattice().isValid());
        assertTrue(!latticeComponent.getDrawing().isEmpty());
        latticeComponent.getAttributeMask().setSelected(0, false);
        latticeComponent.calculateLattice();
        assertTrue(latticeComponent.getLattice().isValid());
        assertTrue(!latticeComponent.getDrawing().isEmpty());

    }

    public static void testCoordinatesAssignment() {

        LatticeComponent component = ComponentsObjectMother.makeLatticeComponentWithSimpleLayoutEngine(new int[][]{
                {0, 1, 1},
                {1, 0, 1},
                {1, 1, 0}
        });
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

    public void testMakeCopy() {
        latticeComponent = ComponentsObjectMother.makeLatticeComponent(new int[][]{{0, 1, 1},
                {1, 0, 1},
                {1, 1, 0}});
        latticeComponent.calculateLattice();
        latticeComponent.setUpLatticeRecalcOnMasksChange();
        LatticeComponent other = latticeComponent.makeCopy();
        assertEquals(latticeComponent.getAttributeMask(), other.getAttributeMask());
        assertEquals(latticeComponent.getObjectMask(), other.getObjectMask());
        assertSame(latticeComponent.getLattice().getContext(), other.getLattice().getContext());
        assertEquals(latticeComponent.getLattice(), other.getLattice());
        assertTrue(latticeComponent.isEqualByContent(other));

        other.getAttributeMask().setSelected(0, false);
        assertEquals(false, latticeComponent.isEqualByContent(other));
        assertEquals(false, latticeComponent.getAttributeMask().equals(other.getAttributeMask()));

    }

    public void testMakeCopy2() {
        latticeComponent = ComponentsObjectMother.makeLatticeComponentWithSimpleLayoutEngine(new int[][]{{0, 1, 1},
                {1, 0, 1},
                {1, 1, 0}});
        latticeComponent.calculateAndLayoutLattice();
        final LatticeSupplier other = latticeComponent.makeCopy();
        assertNotSame(latticeComponent, other);
        final LatticeDrawing firstDrawing = latticeComponent.getDrawing();
        final LatticeDrawing secondDrawing = other.getDrawing();
        latticeComponent.getLattice().forEach(new Lattice.LatticeElementVisitor() {
            public void visitNode(LatticeElement node) {
                final AbstractConceptCorrespondingFigure firstFigure = firstDrawing.getFigureForConcept(node);
                LatticeElement otherNode = secondDrawing.getLattice().findLatticeElementFromOne(node.getAttribs());
                final AbstractConceptCorrespondingFigure secondFigure = secondDrawing.getFigureForConcept(otherNode);
                assertEquals(firstFigure.getCenter(), secondFigure.getCenter());
            }
        });


    }
}
