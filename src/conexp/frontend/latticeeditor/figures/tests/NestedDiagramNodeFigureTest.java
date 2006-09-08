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
import conexp.frontend.latticeeditor.figures.NestedDiagramNodeFigure;
import conexp.frontend.latticeeditor.queries.ConceptNodeQuery;
import conexp.frontend.latticeeditor.queries.GenericNodeQuery;
import junit.framework.TestCase;

public class NestedDiagramNodeFigureTest extends TestCase {
    public static void testGetIntentQuery() {
        Context cxt = SetBuilder.makeContext(new int[][]{{0, 1, 1},
                {1, 0, 1},
                {1, 1, 0}});

        Lattice inner = SetBuilder.makeLatticeWithContextAndFeatureMask(cxt, new int[]{0, 1, 0}, new int[]{1, 1, 1});
        LatticeDrawing innerDrawing = new LatticeDrawing();
        innerDrawing.setLattice(inner);


        Lattice innerMost = SetBuilder.makeLatticeWithContextAndFeatureMask(cxt, new int[]{0, 0, 1}, new int[]{1, 1, 1});
        LatticeDrawing innerMostDrawing = new LatticeDrawing();
        innerMostDrawing.setLattice(innerMost);

        final ConceptNodeQuery nativeConceptQuery = new ConceptNodeQuery(inner.getContext(),
                inner.getZero(),
                inner.getAttributesMask());

        NestedDiagramNodeFigure figure = new NestedDiagramNodeFigure(innerMostDrawing, nativeConceptQuery,
                new GenericNodeQuery(inner.getContext(), SetBuilder.makeSet(new int[]{1, 1, 0}), null, false,
                        SetBuilder.makeSet(new int[]{1, 1, 1})), false);
        assertEquals(SetBuilder.makeSet(new int[]{1, 1, 0}), figure.getIntentQuery());


        figure = new NestedDiagramNodeFigure(innerMostDrawing, nativeConceptQuery,
                null, false);
        assertEquals(SetBuilder.makeSet(new int[]{0, 1, 0}), figure.getIntentQuery());


    }
}
