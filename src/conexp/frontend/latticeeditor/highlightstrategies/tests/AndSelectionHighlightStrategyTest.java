package conexp.frontend.latticeeditor.highlightstrategies.tests;

import conexp.core.Lattice;
import conexp.core.ModifiableSet;
import conexp.core.LatticeElement;
import conexp.core.tests.SetBuilder;
import conexp.frontend.latticeeditor.figures.ConceptFigure;
import conexp.frontend.latticeeditor.highlightstrategies.AndSelectionHighlightStrategy;
import conexp.frontend.latticeeditor.highlightstrategies.NeigboursHighlightStrategy;
import conexp.frontend.latticeeditor.queries.ConceptNodeQuery;
import junit.framework.TestCase;

public class AndSelectionHighlightStrategyTest extends TestCase {
    AndSelectionHighlightStrategy strategy;

    protected void setUp() throws Exception {
        super.setUp();
        strategy = new AndSelectionHighlightStrategy();
    }

    public void testSelectionCombinationCaseNoSelection() {

        ModifiableSet first = SetBuilder.makeSet(new int[]{0, 0, 1});
        ModifiableSet second = SetBuilder.makeSet(new int[]{0, 1, 1});

        assertTrue(strategy.highlightQuery(first));
        assertTrue(strategy.highlightEdge(first, second));
    }

    public void testSelectionCombinationCaseOneNodeInSelection() {
        Lattice lattice = SetBuilder.makeLattice(new int[][]{
                {1, 0},
                {0, 1}
        });

        strategy.addNode(buildNodeForElement(lattice, lattice.getZero()));
        assertTrue(strategy.highlightQuery(lattice.getZero().getAttribs()));
        assertFalse(strategy.highlightQuery(lattice.getOne().getAttribs()));
        assertTrue(strategy.highlightEdge(lattice.getZero().getAttribs(), lattice.getZero().getSucc(0).getAttribs()));
        assertFalse(strategy.highlightEdge(lattice.getOne().getAttribs(), lattice.getOne().getPred(0).getAttribs()));
    }

    private static NeigboursHighlightStrategy buildNodeForElement(Lattice lattice, LatticeElement zero) {
        NeigboursHighlightStrategy nodeHighlightStrategy = new NeigboursHighlightStrategy();
        nodeHighlightStrategy.initFromFigure(new ConceptFigure(ConceptNodeQuery.createNodeQuery(lattice, zero)));
        return nodeHighlightStrategy;
    }

    public void testSelectionnCombinationCaseTwoNodesInSelection() {
        Lattice lattice = SetBuilder.makeLattice(new int[][]{
                {1, 0},
                {0, 1}
        });

        strategy.addNode(buildNodeForElement(lattice, lattice.getZero()));
        strategy.addNode(buildNodeForElement(lattice, lattice.getOne()));

        assertFalse(strategy.highlightQuery(lattice.getZero().getAttribs()));
        assertFalse(strategy.highlightQuery(lattice.getOne().getAttribs()));
        assertTrue(strategy.highlightQuery(lattice.getZero().getSucc(0).getAttribs()));
        assertTrue(strategy.highlightQuery(lattice.getZero().getSucc(1).getAttribs()));

        assertFalse(strategy.highlightEdge(lattice.getZero().getAttribs(), lattice.getZero().getSucc(0).getAttribs()));
        assertFalse(strategy.highlightEdge(lattice.getOne().getAttribs(), lattice.getOne().getPred(0).getAttribs()));

    }

    public void testCreateNew() throws Exception {

        assertTrue(strategy.createNew() instanceof AndSelectionHighlightStrategy);
    }

}