/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor.edgesizecalcstrategies;

import conexp.core.ItemSet;
import conexp.frontend.latticeeditor.AbstractDimensionCalcStrategy;
import conexp.frontend.latticeeditor.EdgeSizeCalcStrategy;


public class FixedEdgeSizeCalcStrategy extends AbstractDimensionCalcStrategy implements EdgeSizeCalcStrategy {
    private float stroke;

    public FixedEdgeSizeCalcStrategy(float stroke) {
        super();
        this.stroke = stroke;
    }

    /**
     * edgeThickness method comment.
     */
    public float edgeThickness(ItemSet start, ItemSet end) {
        return stroke;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FixedEdgeSizeCalcStrategy)) {
            return false;
        }
        FixedEdgeSizeCalcStrategy fixedEdgeSizeCalcStrategy = (FixedEdgeSizeCalcStrategy) obj;
        if (stroke != fixedEdgeSizeCalcStrategy.stroke) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return stroke != +0.0f ? Float.floatToIntBits(stroke) : 0;
    }
}
