/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.latticeeditor.highlightstrategies.tests;

import conexp.frontend.latticeeditor.DrawParameters;
import conexp.frontend.latticeeditor.HighlightStrategy;
import conexp.frontend.latticeeditor.tests.StrategyModelTest;
import conexp.frontend.latticeeditor.highlightstrategies.HighlightStrategyModel;
import conexp.util.gui.strategymodel.StrategyModel;
import conexp.core.ConceptFactory;
import conexp.core.LatticeElement;

public class HighlightStrategyModelTest extends StrategyModelTest {
    /**
     * Insert the method's description here.
     * Creation date: (02.12.00 0:56:09)
     */
    public void testStrategiesWithAndWithoutNode() {
        LatticeElement el1 = ConceptFactory.makeEmptyLatticeElement();
        LatticeElement el2 = ConceptFactory.makeEmptyLatticeElement();

        for (int i = model.getStrategiesCount(); --i >= 0;) {
            HighlightStrategy strategy =
                    (HighlightStrategy) model.getStrategy(i);
            strategy.highlightEdge(el1.getAttribs(), el2.getAttribs());
            strategy.highlightNodeWithQuery(el1.getAttribs());

            strategy.setNode(el1);
            strategy.highlightEdge(el1.getAttribs(), el2.getAttribs());
            strategy.highlightNodeWithQuery(el1.getAttribs());
        }

    }

    /**
     * createStrategiesModel method comment.
     */
    protected StrategyModel createStrategiesModel(DrawParameters opt) {
        return new HighlightStrategyModel(opt);
    }
}
