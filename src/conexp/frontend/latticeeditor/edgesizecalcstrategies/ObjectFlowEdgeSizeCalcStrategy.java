package conexp.frontend.latticeeditor.edgesizecalcstrategies;

import conexp.core.ItemSet;
import conexp.frontend.latticeeditor.DrawParameters;

/**
 * Insert the type's description here.
 * Creation date: (18.01.01 23:21:34)
 * @author
 */
public class ObjectFlowEdgeSizeCalcStrategy extends conexp.frontend.latticeeditor.DefaultDimensionCalcStrategy implements conexp.frontend.latticeeditor.EdgeSizeCalcStrategy {
    /**
     * OnePixelEdgeSizeCalcStrategy constructor comment.
     * @param opt conexp.frontend.latticeeditor.LatticePainterOptions
     */
    public ObjectFlowEdgeSizeCalcStrategy(DrawParameters opt) {
        super(opt);
    }

    /**
     * edgeThickness method comment.
     */
    public float edgeThickness(ItemSet start, ItemSet end) {
        if (!isInit()) {
            return 0;
        }
        return Math.max(1.0f, options.getMaxEdgeStroke() * ((float) start.getObjCnt() / getContext().getObjectCount()));
    }
}