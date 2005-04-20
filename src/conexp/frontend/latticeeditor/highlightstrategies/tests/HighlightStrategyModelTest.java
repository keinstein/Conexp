/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.latticeeditor.highlightstrategies.tests;

import conexp.frontend.latticeeditor.DrawParameters;
import conexp.frontend.latticeeditor.highlightstrategies.HighlightStrategyModel;
import conexp.util.gui.strategymodel.StrategyModel;

public class HighlightStrategyModelTest extends conexp.frontend.latticeeditor.tests.StrategyModelTest {
    /**
     * Insert the method's description here.
     * Creation date: (02.12.00 0:56:09)
     */
    public void testStrategiesWithAndWithoutNode() {
        conexp.core.LatticeElement el1 = conexp.core.ConceptFactory.makeEmptyLatticeElement();
        conexp.core.LatticeElement el2 = conexp.core.ConceptFactory.makeEmptyLatticeElement();

        for (int i = model.getStrategiesCount(); --i >= 0;) {
            conexp.frontend.latticeeditor.HighlightStrategy strategy =
                    (conexp.frontend.latticeeditor.HighlightStrategy) model.getStrategy(i);
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
