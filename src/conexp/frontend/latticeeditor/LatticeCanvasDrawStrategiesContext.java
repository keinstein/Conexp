/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/

package conexp.frontend.latticeeditor;

import conexp.core.layout.Layouter;
import conexp.util.gui.paramseditor.ParamInfo;
import conexp.util.gui.paramseditor.StrategyValueItemParamInfo;
import conexp.util.gui.strategymodel.StrategyValueItem;

import java.beans.PropertyChangeSupport;


public class LatticeCanvasDrawStrategiesContext extends BasicStrategiesContext implements DrawStrategiesContext {

    public Layouter getLayouter() {
        return (Layouter) getLayoutStrategyItem().getStrategy();
    }

    private StrategyValueItem layoutStrategy;

    synchronized StrategyValueItem getLayoutStrategyItem() {
        if (null == layoutStrategy) {
            layoutStrategy = makeStrategyValueItem("layout", factory.makeLayoutStrategiesModel());
        }
        return layoutStrategy;
    }

    private StrategyValueItem highlightStrategy;
    private StrategyValueItem nodeRadiusStrategy;
    private StrategyValueItem edgeSizeCalcStrategy;
    private DrawStrategiesModelsFactory factory;

    /**
     * LatticePainterOptions constructor comment.
     */
    public LatticeCanvasDrawStrategiesContext(DrawStrategiesModelsFactory factory, PropertyChangeSupport propertyChange) {
        super(propertyChange);
        this.factory = factory;
    }

    public void setupStrategiesParams(conexp.core.ConceptsCollection conceptSet) {
        getFigureDimensionCalcStrategy().setConceptSet(conceptSet);
        getFigureDimensionCalcStrategy().initCalc();
        getEdgeSizeCalcStrategy().setConceptSet(conceptSet);
        getEdgeSizeCalcStrategy().initCalc();
    }

    public EdgeSizeCalcStrategy getEdgeSizeCalcStrategy() {
        return (EdgeSizeCalcStrategy) getEdgeSizeCalcStrategyItem().getStrategy();
    }

    synchronized StrategyValueItem getEdgeSizeCalcStrategyItem() {
        if (null == edgeSizeCalcStrategy) {
            edgeSizeCalcStrategy = makeStrategyValueItem("edgeDrawStrategy", factory.makeEdgeSizeStrategiesModel());
        }
        return edgeSizeCalcStrategy;
    }

    public HighlightStrategy getHighlightStrategy() {
        return (HighlightStrategy) getHighlightStrategyItem().getStrategy();
    }

    synchronized StrategyValueItem getHighlightStrategyItem() {
        if (null == highlightStrategy) {
            highlightStrategy = makeStrategyValueItem("highlightStrategy", factory.makeHighlightStrategiesModel());
        }
        return highlightStrategy;
    }


    public FigureDimensionCalcStrategy getFigureDimensionCalcStrategy() {
        return (FigureDimensionCalcStrategy) getNodeRadiusStrategyItem().getStrategy();
    }

    public synchronized StrategyValueItem getNodeRadiusStrategyItem() {
        if (null == nodeRadiusStrategy) {
            nodeRadiusStrategy = makeStrategyValueItem("nodeDrawStrategy", factory.makeNodeRadiusStrategiesModel());
        }
        return nodeRadiusStrategy;
    }


    protected ParamInfo[] makeParamInfo() {
        return new ParamInfo[]{
            new StrategyValueItemParamInfo("Layout", getLayoutStrategyItem()),
            new StrategyValueItemParamInfo("Draw node", getNodeRadiusStrategyItem()),
            new StrategyValueItemParamInfo("Draw edge", getEdgeSizeCalcStrategyItem()),
            new StrategyValueItemParamInfo("Highlight", getHighlightStrategyItem())
        };
    }
}
