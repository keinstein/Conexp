package conexp.frontend.latticeeditor.edgesizecalcstrategies;

import conexp.frontend.latticeeditor.DrawParameters;

/**
 * Insert the type's description here.
 * Creation date: (18.01.01 23:52:52)
 * @author
 */
public class EdgeSizeStrategyModel extends conexp.frontend.latticeeditor.AbstractDrawingStrategyModel {
    /**
     * EdgeSizeStrategyModel constructor comment.
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
        setStrategy(i++, "InvisibleEdgeStrategyModel" , "No ", new FixedEdgeSizeCalcStrategy(0));
        setStrategy(i++, "~ object", new ObjectFlowEdgeSizeCalcStrategy(opt));
        setStrategy(i, "~ connection", new ConceptConnectionEdgeSizeCalcStrategy(opt));
    }
}