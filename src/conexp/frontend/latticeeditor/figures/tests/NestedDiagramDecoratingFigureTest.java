/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor.figures.tests;

import conexp.core.Context;
import conexp.core.Lattice;
import conexp.core.tests.SetBuilder;
import conexp.frontend.latticeeditor.LatticeDrawing;
import conexp.frontend.latticeeditor.figures.NestedDiagramDecoratingFigure;
import junit.framework.TestCase;

public class NestedDiagramDecoratingFigureTest extends TestCase {
    public static void testGetIntentQuery() {
        Context cxt = SetBuilder.makeContext(new int[][]{{0, 1},
                {1, 0}});

        Lattice outer = SetBuilder.makeLatticeWithContextAndFeatureMask(cxt, new int[]{1, 0}, new int[]{1, 1});
        LatticeDrawing outerDrawing = new LatticeDrawing();
        outerDrawing.setLattice(outer);

        Lattice inner = SetBuilder.makeLatticeWithContextAndFeatureMask(cxt, new int[]{0, 1}, new int[]{1, 1});
        LatticeDrawing innerDrawing = new LatticeDrawing();
        innerDrawing.setLattice(inner);

        NestedDiagramDecoratingFigure figure = new NestedDiagramDecoratingFigure(innerDrawing.getFigureForConcept(inner.getZero()),

                outerDrawing.getFigureForConcept(outer.getZero()).getConceptQuery(), false);
        assertEquals(SetBuilder.makeSet(new int[]{1, 1}), figure.getIntentQuery());

        figure = new NestedDiagramDecoratingFigure(innerDrawing.getFigureForConcept(inner.getOne()),
                outerDrawing.getFigureForConcept(outer.getOne()).getConceptQuery(), false);
        assertEquals(SetBuilder.makeSet(new int[]{0, 0}), figure.getIntentQuery());
    }
}
