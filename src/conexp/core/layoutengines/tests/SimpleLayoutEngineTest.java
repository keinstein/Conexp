/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.core.layoutengines.tests;

import conexp.core.Context;
import conexp.core.FCAEngineRegistry;
import conexp.core.Lattice;
import conexp.core.LatticeElement;
import conexp.core.layoutengines.SimpleLayoutEngine;
import conexp.frontend.components.LatticeComponent;
import conexp.frontend.latticeeditor.LatticeDrawing;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import util.collection.CollectionFactory;

import java.util.Set;

public class SimpleLayoutEngineTest extends TestCase {
    private static final Class THIS = SimpleLayoutEngineTest.class;

    public static Test suite() {
        return new TestSuite(THIS);
    }

    public void testCoordinatesAssignment() {

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
        System.out.println(distinctCoors.size());
    }
}
