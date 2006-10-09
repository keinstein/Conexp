/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor.highlightstrategies.tests;

import conexp.core.ConceptFactory;
import conexp.core.LatticeElement;
import conexp.core.Context;
import conexp.core.tests.SetBuilder;
import conexp.frontend.latticeeditor.DrawParameters;
import conexp.frontend.latticeeditor.Highlighter;
import conexp.frontend.latticeeditor.ConceptHighlightStrategy;
import conexp.frontend.latticeeditor.ConceptHighlightAtomicStrategy;
import conexp.frontend.latticeeditor.queries.ConceptNodeQuery;
import conexp.frontend.latticeeditor.queries.tests.ConceptNodeQueryFactory;
import conexp.frontend.latticeeditor.figures.ConceptFigure;
import conexp.frontend.latticeeditor.highlightstrategies.HighlightStrategyModel;
import conexp.frontend.latticeeditor.tests.StrategyModelTest;
import conexp.util.gui.strategymodel.StrategyModel;

import java.util.LinkedHashSet;
import java.util.Arrays;

public class HighlightStrategyModelTest extends StrategyModelTest {
    public void testStrategiesWithAndWithoutSelection() {
        LatticeElement el1 = ConceptFactory.makeEmptyLatticeElement();
        LatticeElement el2 = ConceptFactory.makeEmptyLatticeElement();


        for (int i = model.getStrategiesCount(); --i >= 0;) {
            Highlighter highlighter = new Highlighter();
            highlighter.setConceptHighlightStrategy(
                    (ConceptHighlightAtomicStrategy) model.getStrategy(i));

            highlighter.highlightEdge(el1.getAttribs(), el2.getAttribs());
            highlighter.highlightNodeWithQuery(el1.getAttribs());

            highlighter.setSelectedConcepts(new LinkedHashSet(Arrays.asList(new Object[]{
                    new ConceptFigure(
                    ConceptNodeQueryFactory.makeEmpty())}
            )));
            highlighter.highlightEdge(el1.getAttribs(), el2.getAttribs());
            highlighter.highlightNodeWithQuery(el1.getAttribs());
        }
    }

    /**
     * createStrategiesModel method comment.
     */
    protected StrategyModel createStrategiesModel(DrawParameters opt) {
        return new HighlightStrategyModel(opt);
    }
}
