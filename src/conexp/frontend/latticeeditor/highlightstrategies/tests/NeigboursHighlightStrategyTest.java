/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.frontend.latticeeditor.highlightstrategies.tests;

import conexp.frontend.latticeeditor.highlightstrategies.FilterIdealHighlightStrategy;
import conexp.frontend.latticeeditor.highlightstrategies.NeigboursHighlightStrategy;
import conexp.frontend.latticeeditor.tests.HighlightStrategyTest;
import conexp.frontend.latticeeditor.ConceptHighlightStrategy;
import conexp.frontend.latticeeditor.figures.ConceptFigure;
import conexp.frontend.latticeeditor.queries.ConceptNodeQuery;
import conexp.core.Lattice;
import conexp.core.LatticeElement;
import conexp.core.tests.SetBuilder;


public class NeigboursHighlightStrategyTest extends HighlightStrategyTest {
    protected ConceptHighlightStrategy makeNotEqualInstance() {
        return new FilterIdealHighlightStrategy();
    }

    protected ConceptHighlightStrategy makeEqualInstance() {
        return new NeigboursHighlightStrategy();
    }

    public void testHighlightingOfUpperAndLowerNeighbors(){
        NeigboursHighlightStrategy strategy = new NeigboursHighlightStrategy();
        Lattice lattice = SetBuilder.makeLattice(new int[][]{
                {1, 0, 0},
                {0, 1, 0},
                {0, 0, 1}
        });
        LatticeElement left = lattice.findElementWithIntent(SetBuilder.makeSet(new int[]{1, 0, 0}));
        LatticeElement middle = lattice.findElementWithIntent(SetBuilder.makeSet(new int[]{0, 1, 0}));
        LatticeElement right = lattice.findElementWithIntent(SetBuilder.makeSet(new int[]{0, 0, 1}));

        strategy.initFromFigure(buildFigureForConcept(lattice, middle));


        assertTrue(strategy.highlightQuery(middle.getAttribs()));
        assertFalse(strategy.highlightQuery(left.getAttribs()));
        assertFalse(strategy.highlightQuery(right.getAttribs()));

        assertEquals(1, middle.getPredCount());
        assertTrue(strategy.highlightEdge(middle.getAttribs(), middle.getPred(0).getAttribs()));
        assertEquals(1, middle.getSuccCount());
        assertTrue(strategy.highlightEdge(middle.getAttribs(), middle.getSucc(0).getAttribs()));

        assertTrue(strategy.highlightQuery(lattice.getZero().getAttribs()));
        assertTrue(strategy.highlightQuery(lattice.getOne().getAttribs()));

    }

    private static ConceptFigure buildFigureForConcept(Lattice lattice, LatticeElement middle) {
        return new ConceptFigure(ConceptNodeQuery.createNodeQuery(lattice, middle));
    }
}
