package conexp.frontend.latticeeditor.tests;

import conexp.frontend.latticeeditor.Highlighter;
import conexp.frontend.latticeeditor.highlightstrategies.NoHighlightStrategy;
import conexp.frontend.latticeeditor.figures.ConceptFigure;
import conexp.frontend.latticeeditor.queries.tests.ConceptNodeQueryFactory;
import junit.framework.TestCase;

import java.util.LinkedHashSet;

import canvas.highlightstrategies.NullHighlightStrategy;

public class HighlighterTest extends TestCase {
    Highlighter highlighter;


    protected void setUp() throws Exception {
        super.setUp();
        highlighter = new Highlighter();
        highlighter.setConceptHighlightStrategy(NoHighlightStrategy.getInstance());
    }

    public void testShouldBeInActiveWhenSelectionIsEmpty(){
        highlighter.setSelectedConcepts(new LinkedHashSet());
        assertFalse(highlighter.isActive());
    }

    public void testShouldBeActiveWhenSelectionIsNotEmpty(){
        LinkedHashSet figuresSet = new LinkedHashSet();
        figuresSet.add(new ConceptFigure(ConceptNodeQueryFactory.makeWithOwnAttribs()));
        highlighter.setSelectedConcepts(figuresSet);
        assertTrue(highlighter.isActive());
    }

}