/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor.edgesizecalcstrategies;

import conexp.frontend.latticeeditor.AbstractDrawingStrategyModel;
import conexp.frontend.latticeeditor.DrawParameters;


public class EdgeSizeStrategyModel extends AbstractDrawingStrategyModel {
    /**
     * EdgeSizeStrategyModel constructor comment.
     *
     * @param opt conexp.frontend.latticeeditor.LatticePainterOptions
     */
    public EdgeSizeStrategyModel(DrawParameters opt) {
        super(opt);
    }

    /**
     * createStrategies method comment.
     */
    protected void createStrategies(DrawParameters opt) {
        allocateStrategies(4);
        int i = 0;
        setStrategy(i++, "One pixel", new FixedEdgeSizeCalcStrategy(1.0f));
        setStrategy(i++, "InvisibleEdgeStrategyModel", "No ", new FixedEdgeSizeCalcStrategy(0));
        setStrategy(i++, "~ object", new ObjectFlowEdgeSizeCalcStrategy(opt));
        setStrategy(i, "~ connection", new ConceptConnectionEdgeSizeCalcStrategy(opt));
    }
}
