/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor.edgesizecalcstrategies;

import conexp.core.ItemSet;
import conexp.frontend.latticeeditor.DefaultDimensionCalcStrategy;
import conexp.frontend.latticeeditor.DrawParameters;
import conexp.frontend.latticeeditor.EdgeSizeCalcStrategy;


public class ConceptConnectionEdgeSizeCalcStrategy extends DefaultDimensionCalcStrategy implements EdgeSizeCalcStrategy {
    /**
     * OnePixelEdgeSizeCalcStrategy constructor comment.
     *
     * @param opt conexp.frontend.latticeeditor.LatticePainterOptions
     */
    public ConceptConnectionEdgeSizeCalcStrategy(DrawParameters opt) {
        super(opt);
    }

    /**
     * edgeThickness method comment.
     */
    public float edgeThickness(ItemSet start, ItemSet end) {
        if (!isInit()) {
            return 0;
        }
        if (0 == end.getObjCnt()) {
            return 1.0f;
        }
        return (float) Math.max(1.0f, options.getMaxEdgeStroke() * ((double) start.getObjCnt() / end.getObjCnt()));
    }

}
