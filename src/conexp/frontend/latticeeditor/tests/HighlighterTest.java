package conexp.frontend.latticeeditor.tests;

import conexp.frontend.latticeeditor.ConceptHighlightAtomicStrategy;
import conexp.frontend.latticeeditor.ConceptHighlightStrategyCombination;
import conexp.frontend.latticeeditor.Highlighter;
import conexp.frontend.latticeeditor.figures.ConceptFigure;
import conexp.frontend.latticeeditor.highlightstrategies.NoHighlightStrategy;
import conexp.frontend.latticeeditor.queries.tests.ConceptNodeQueryFactory;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import java.util.Arrays;
import java.util.LinkedHashSet;

public class HighlighterTest extends MockObjectTestCase {
    Highlighter highlighter;


    protected void setUp() throws Exception {
        super.setUp();
        highlighter = new Highlighter();
        highlighter.setConceptHighlightStrategy(NoHighlightStrategy.getInstance());
    }

    public void testShouldBeInActiveWhenSelectionIsEmpty() {
        highlighter.setSelectedConcepts(new LinkedHashSet());
        assertFalse(highlighter.isActive());
    }

    public void testShouldBeActiveWhenSelectionIsNotEmpty() {
        LinkedHashSet figuresSet = new LinkedHashSet();
        figuresSet.add(new ConceptFigure(ConceptNodeQueryFactory.makeWithOwnAttribs()));
        highlighter.setSelectedConcepts(figuresSet);
        assertTrue(highlighter.isActive());
    }

    public void testSetSelectionCombination() throws Exception {

        Mock selectionCombinationMock = mock(ConceptHighlightStrategyCombination.class);
        selectionCombinationMock.expects(once()).method("clear").withNoArguments();

        Mock conceptHighlightStrategyMock = mock(ConceptHighlightAtomicStrategy.class);

        ConceptFigure firstFigure = new ConceptFigure(ConceptNodeQueryFactory.makeWithOwnAttribs());
        ConceptFigure secondFigure = new ConceptFigure(ConceptNodeQueryFactory.makeWithOwnObjects());

        ConceptHighlightAtomicStrategy first = makeMockExpectingMethodCallForFigure("initFromFigure", firstFigure);
        ConceptHighlightAtomicStrategy second = makeMockExpectingMethodCallForFigure("initFromFigure", secondFigure);

        conceptHighlightStrategyMock.expects(atLeastOnce()).method("createNew").withNoArguments().
                will(onConsecutiveCalls(
                        returnValue(first),
                        returnValue(second)
                ));

        selectionCombinationMock.expects(once()).method("addNode").with(same(first));
        selectionCombinationMock.expects(once()).method("addNode").with(same(second));

        highlighter.setSelectionCombination((ConceptHighlightStrategyCombination) selectionCombinationMock.proxy());
        highlighter.setConceptHighlightStrategy((ConceptHighlightAtomicStrategy)
                conceptHighlightStrategyMock.proxy());

        LinkedHashSet aSelection = new LinkedHashSet(Arrays.asList(
                new Object[]{
                        firstFigure,
                        secondFigure
                }
        ));
        assertEquals(2, aSelection.size());
        highlighter.setSelectedConcepts(aSelection);
    }

    private ConceptHighlightAtomicStrategy makeMockExpectingMethodCallForFigure(String methodName,
                                                                                ConceptFigure firstFigure) {
        Mock mock = mock(ConceptHighlightAtomicStrategy.class);
        mock.expects(once()).method(methodName).with(same(firstFigure));
        ConceptHighlightAtomicStrategy first = (ConceptHighlightAtomicStrategy) mock.proxy();
        return first;
    }
}