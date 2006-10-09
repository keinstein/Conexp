/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor;

import conexp.core.ConceptsCollection;
import conexp.core.layout.Layouter;
import conexp.util.gui.paramseditor.ParamInfo;
import conexp.util.gui.paramseditor.StrategyValueItemParamInfo;
import conexp.util.gui.strategymodel.StrategyValueItem;

import java.beans.PropertyChangeSupport;
import java.util.prefs.Preferences;


public class LatticeCanvasDrawStrategiesContext extends BasicStrategiesContext implements DrawStrategiesContext, LatticeCanvasDrawStrategiesContextProperties {
    /**
     * LatticePainterOptions constructor comment.
     */
    public LatticeCanvasDrawStrategiesContext(DrawStrategiesModelsFactory factory, PropertyChangeSupport propertyChange) {
        super(propertyChange);
        this.factory = factory;
        highlighter = new Highlighter();

        setPreferences(Preferences.userNodeForPackage(LatticeCanvasDrawStrategiesContext.class));
/*
        printPreferences();
        System.out.println("LatticeCanvasDrawStrategiesContext.LatticeCanvasDrawStrategiesContext");
*/
    }

    public Layouter getLayouter() {
        return (Layouter) getLayoutStrategyItem().getStrategy();
    }

    private StrategyValueItem layoutStrategy;

    public synchronized StrategyValueItem getLayoutStrategyItem() {
        if (null == layoutStrategy) {
            layoutStrategy = makeStrategyValueItem(LAYOUT_PROPERTY, factory.makeLayoutStrategiesModel());
        }
        return layoutStrategy;
    }

    private StrategyValueItem highlightStrategy;
    private StrategyValueItem nodeRadiusStrategy;
    private StrategyValueItem edgeSizeCalcStrategy;
    private DrawStrategiesModelsFactory factory;


    public void setupStrategiesParams(ConceptsCollection conceptSet) {
        getFigureDimensionCalcStrategy().setConceptSet(conceptSet);
        getFigureDimensionCalcStrategy().initCalc();
        getEdgeSizeCalcStrategy().setConceptSet(conceptSet);
        getEdgeSizeCalcStrategy().initCalc();
    }

    public EdgeSizeCalcStrategy getEdgeSizeCalcStrategy() {
        return (EdgeSizeCalcStrategy) getEdgeSizeCalcStrategyItem().getStrategy();
    }

    public synchronized StrategyValueItem getEdgeSizeCalcStrategyItem() {
        if (null == edgeSizeCalcStrategy) {
            edgeSizeCalcStrategy = makeStrategyValueItem(EDGE_DRAW_STRATEGY_PROPERTY, factory.makeEdgeSizeStrategiesModel());
        }
        return edgeSizeCalcStrategy;
    }

    Highlighter highlighter;

    public Highlighter getHighlighter() {
        //todo:sye - think about better way to handle setting of concept highlight strategy (in reaction on selection change);
        highlighter.setConceptHighlightStrategy((ConceptHighlightAtomicStrategy)getHighlightStrategyItem().getStrategy());
        return highlighter;
    }

    public synchronized StrategyValueItem getHighlightStrategyItem() {
        if (null == highlightStrategy) {
            highlightStrategy = makeStrategyValueItem(HIGHLIGHT_STRATEGY_PROPERTY, factory.makeHighlightStrategiesModel());
        }
        return highlightStrategy;
    }

    public FigureDimensionCalcStrategy getFigureDimensionCalcStrategy() {
        return (FigureDimensionCalcStrategy) getNodeRadiusStrategyItem().getStrategy();
    }

    public synchronized StrategyValueItem getNodeRadiusStrategyItem() {
        if (null == nodeRadiusStrategy) {
            nodeRadiusStrategy = makeStrategyValueItem(NODE_DRAW_STRATEGY_PROPERTY, factory.makeNodeRadiusStrategiesModel());
        }
        return nodeRadiusStrategy;
    }

    public void restorePreferences() {
        doRestorePreferences(getPreferences());
    }

    private void doRestorePreferences(Preferences preferences) {
        getLayoutStrategyItem().restoreFromPreferences(preferences);
        getNodeRadiusStrategyItem().restoreFromPreferences(preferences);
        getEdgeSizeCalcStrategyItem().restoreFromPreferences(preferences);
        getHighlightStrategyItem().restoreFromPreferences(preferences);
    }

    public void doStorePreferences() {
        doStorePreferences(getPreferences());
    }

    public String getFigureDimensionStrategyKey() {
        return getNodeRadiusStrategyItem().getStrategyKey();
    }

    public boolean setFigureDimensionStrategyKey(String key) {
        return getNodeRadiusStrategyItem().setValueByKey(key);
    }

    public String getEdgeSizeStrategyKey() {
        return getEdgeSizeCalcStrategyItem().getStrategyKey();
    }

    public boolean setEdgeSizeStrategyKey(String key) {
        return getEdgeSizeCalcStrategyItem().setValueByKey(key);
    }

    public String getHighlightStrategyKey() {
        return getHighlightStrategyItem().getStrategyKey();
    }

    public boolean setHighlightStrategyKey(String key) {
        return getHighlightStrategyItem().setValueByKey(key);
    }

    public void doStorePreferences(Preferences preferences) {
        getLayoutStrategyItem().storeToPreferences(preferences);
        getNodeRadiusStrategyItem().storeToPreferences(preferences);
        getEdgeSizeCalcStrategyItem().storeToPreferences(preferences);
        getHighlightStrategyItem().storeToPreferences(preferences);
    }


    private void readInitialValues(LatticeCanvasDrawStrategiesContext other) {
        getLayoutStrategyItem().setValue(other.getLayoutStrategyItem().getValue());
        getNodeRadiusStrategyItem().setValue(other.getNodeRadiusStrategyItem().getValue());
        getEdgeSizeCalcStrategyItem().setValue(other.getEdgeSizeCalcStrategyItem().getValue());
        getHighlightStrategyItem().setValue(other.getHighlightStrategyItem().getValue());
    }

    protected ParamInfo[] makeParamInfo() {
        return new ParamInfo[]{
                new StrategyValueItemParamInfo("Layout", getLayoutStrategyItem()),
                new StrategyValueItemParamInfo("Draw node", getNodeRadiusStrategyItem()),
                new StrategyValueItemParamInfo("Draw edge", getEdgeSizeCalcStrategyItem()),
                new StrategyValueItemParamInfo("Highlight", getHighlightStrategyItem())
        };
    }


    public String toString() {
        return "LatticeCanvasDrawStrategiesContext{" +
                "layoutStrategy=" + layoutStrategy +
                ", nodeRadiusStrategy=" + nodeRadiusStrategy +
                ", edgeSizeCalcStrategy=" + edgeSizeCalcStrategy +
                ", highlightStrategy=" + highlightStrategy +
                '}';
    }

    public DrawStrategiesContext makeCopy(PropertyChangeSupport propertyChange) {
        return makeNativeCopy(propertyChange);
    }

    public LatticeCanvasDrawStrategiesContext makeNativeCopy(PropertyChangeSupport propertyChange) {
        LatticeCanvasDrawStrategiesContext ret = new LatticeCanvasDrawStrategiesContext(factory.makeCopy(), propertyChange);
        ret.readInitialValues(this);
        return ret;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof LatticeCanvasDrawStrategiesContext)) {
            return false;
        }

        final LatticeCanvasDrawStrategiesContext other = (LatticeCanvasDrawStrategiesContext) obj;

        if (edgeSizeCalcStrategy != null ? !edgeSizeCalcStrategy.equals(other.edgeSizeCalcStrategy) : other.edgeSizeCalcStrategy != null)
        {
            return false;
        }
        if (highlightStrategy != null ? !highlightStrategy.equals(other.highlightStrategy) : other.highlightStrategy != null)
        {
            return false;
        }
        if (layoutStrategy != null ? !layoutStrategy.equals(other.layoutStrategy) : other.layoutStrategy != null) {
            return false;
        }
        if (nodeRadiusStrategy != null ? !nodeRadiusStrategy.equals(other.nodeRadiusStrategy) : other.nodeRadiusStrategy != null)
        {
            return false;
        }

        return true;
    }

    public int hashCode() {
        int result;
        result = layoutStrategy != null ? layoutStrategy.hashCode() : 0;
        result = 29 * result + (highlightStrategy != null ? highlightStrategy.hashCode() : 0);
        result = 29 * result + (nodeRadiusStrategy != null ? nodeRadiusStrategy.hashCode() : 0);
        result = 29 * result + (edgeSizeCalcStrategy != null ? edgeSizeCalcStrategy.hashCode() : 0);
        return result;
    }

}
