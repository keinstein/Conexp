/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.layoutengines.tests;

import conexp.core.Context;
import conexp.core.FCAEngineRegistry;
import conexp.core.Lattice;
import conexp.core.LatticeElement;
import conexp.core.layout.ConceptCoordinateMapper;
import conexp.core.layout.DefaultLayoutParameters;
import conexp.core.layout.MinIntersectionLayouterProvider;
import conexp.core.layoutengines.LayoutEngine;
import conexp.core.layoutengines.LayoutListener;
import conexp.core.layoutengines.SimpleLayoutEngine;
import junit.framework.TestCase;
import util.collection.CollectionFactory;

import java.awt.geom.Point2D;
import java.util.Set;

public class SimpleLayoutEngineTest extends TestCase {

    public static void testNativeCoordinatesAssignment() {

        Context cxt = FCAEngineRegistry.makeContext(3, 3);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (i != j) {
                    cxt.setRelationAt(i, j, true);
                }
            }
        }

        final Lattice lattice = FCAEngineRegistry.buildLattice(cxt);

        LayoutEngine layoutEngine = new SimpleLayoutEngine();
        final Set distinctCoors = CollectionFactory.createDefaultSet();


        layoutEngine.addLayoutListener(new LayoutListener() {
            Point2D.Double point = new Point2D.Double();

            public void layoutChange(final ConceptCoordinateMapper mapper) {
                lattice.forEach(new Lattice.LatticeElementVisitor() {
                    public void visitNode(LatticeElement node) {
                        mapper.setCoordsForConcept(node, point);
                        distinctCoors.add(point.clone());
                    }
                });
            }
        });

        MinIntersectionLayouterProvider layouterProvider = new MinIntersectionLayouterProvider();
        layoutEngine.init(layouterProvider);
        layoutEngine.startLayout(lattice, new DefaultLayoutParameters());
        assertEquals(8, distinctCoors.size());
    }

}
