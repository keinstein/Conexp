/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor.highlightstrategies;


import conexp.frontend.latticeeditor.AbstractDrawingStrategyModel;
import conexp.frontend.latticeeditor.DrawParameters;

public class HighlightStrategyModel extends AbstractDrawingStrategyModel {
    private final static int FILTER_IDEAL_HIGHLIGHT_STRATEGY = 0;
    private final static int ONE_NODE_HIGHLIGHT_STRATEGY = FILTER_IDEAL_HIGHLIGHT_STRATEGY + 1;
    private final static int NEIGHBOURS_HIGHLIGHT_STRATEGY = ONE_NODE_HIGHLIGHT_STRATEGY + 1;
    private final static int IDEAL_HIGHLIGHT_STRATEGY = NEIGHBOURS_HIGHLIGHT_STRATEGY + 1;
    private final static int FILTER_HIGHLIGHT_STRATEGY = IDEAL_HIGHLIGHT_STRATEGY + 1;
    private final static int NO_HIGHLIGHT_STRATEGY = FILTER_HIGHLIGHT_STRATEGY + 1;
    private final static int LAST_STRATEGY = NO_HIGHLIGHT_STRATEGY;
    private final static int STRATEGY_COUNT = LAST_STRATEGY + 1;


    /**
     * HighlightStrategyModel constructor comment.
     */
    public HighlightStrategyModel(DrawParameters opt) {
        super(opt);
    }

    /**
     * Insert the method's description here.
     * Creation date: (01.12.00 5:01:08)
     */
    protected void createStrategies(DrawParameters opt) {
        allocateStrategies(STRATEGY_COUNT);
        setStrategy(NO_HIGHLIGHT_STRATEGY, "No ", NoHighlightStrategy.getInstance());
        setStrategy(ONE_NODE_HIGHLIGHT_STRATEGY, "Selected", new OneNodeHighlightStrategy());
        setStrategy(NEIGHBOURS_HIGHLIGHT_STRATEGY, "Neighbours", new NeigboursHighlightStrategy());
        setStrategy(IDEAL_HIGHLIGHT_STRATEGY, "Ideal", new IdealHighlightStrategy());
        setStrategy(FILTER_HIGHLIGHT_STRATEGY, "Filter", new FilterHighlightStrategy());
        setStrategy(FILTER_IDEAL_HIGHLIGHT_STRATEGY, "Filter & Ideal", new FilterIdealHighlightStrategy());
    }
}
