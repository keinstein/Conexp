package conexp.frontend.latticeeditor.edgesizecalcstrategies;

import conexp.core.ItemSet;
import conexp.frontend.latticeeditor.AbstractDimensionCalcStrategy;
import conexp.frontend.latticeeditor.EdgeSizeCalcStrategy;

/**
 * Insert the type's description here.
 * Creation date: (18.01.01 23:21:34)
 * @author
 */
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
}