package conexp.frontend.latticeeditor.figures.tests;

import conexp.core.Context;
import conexp.core.Lattice;
import conexp.core.tests.SetBuilder;
import conexp.frontend.latticeeditor.LatticeDrawing;
import conexp.frontend.latticeeditor.figures.NestedDiagramNodeFigure;
import conexp.frontend.latticeeditor.queries.ConceptNodeQuery;
import conexp.frontend.latticeeditor.queries.GenericNodeQuery;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class NestedDiagramNodeFigureTest extends TestCase {
    private static final Class THIS = NestedDiagramNodeFigureTest.class;

    public static Test suite() {
        return new TestSuite(THIS);
    }

    public void testGetIntentQuery() {
        Context cxt = SetBuilder.makeContext(new int[][]{{0, 1, 1},
                                                         {1, 0, 1},
                                                         {1, 1, 0}});

        Lattice inner = SetBuilder.makeLatticeWithContextAndFeatureMask(cxt, new int[]{0, 1, 0});
        LatticeDrawing innerDrawing = new LatticeDrawing();
        innerDrawing.setLattice(inner);


        Lattice innerMost = SetBuilder.makeLatticeWithContextAndFeatureMask(cxt, new int[]{0, 0, 1});
        LatticeDrawing innerMostDrawing = new LatticeDrawing();
        innerMostDrawing.setLattice(innerMost);

        final ConceptNodeQuery nativeConceptQuery = new ConceptNodeQuery(inner.getContext(),
                inner.getZero(),
                inner.getFeatureMask());

        NestedDiagramNodeFigure figure = new NestedDiagramNodeFigure(
                innerMostDrawing, nativeConceptQuery,
                new GenericNodeQuery(inner.getContext(), SetBuilder.makeSet(new int[]{1, 1, 0}), null, false,
                        SetBuilder.makeSet(new int[]{1, 1, 1})), false);
        assertEquals(SetBuilder.makeSet(new int[]{1, 1, 0}), figure.getIntentQuery());


        figure = new NestedDiagramNodeFigure(
                innerMostDrawing, nativeConceptQuery,
                null, false);
        assertEquals(SetBuilder.makeSet(new int[]{0, 1, 0}), figure.getIntentQuery());


    }
}