/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.latticeeditor;

import canvas.IHighlightStrategy;
import conexp.core.layout.Layouter;
import conexp.util.gui.paramseditor.BoundedIntValueParamInfo;
import conexp.util.gui.paramseditor.ParamInfo;
import conexp.util.gui.paramseditor.ParamsProvider;
import conexp.util.valuemodels.BoundedIntValue;
import util.BaseVetoablePropertyChangeSupplier;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

//todo: rename to LatticeCanvasSchemeWithOptions

public class LatticePainterOptions extends BaseVetoablePropertyChangeSupplier implements LatticeCanvasScheme, ParamsProvider, LatticeCanvasSchemeProperties {
    private BoundedIntValue smallGridSize;


    private LatticeCanvasDrawStrategiesContext drawStrategiesContext;

    private canvas.CanvasColorScheme colorScheme = new canvas.DefaultColorScheme();

    private ModelsFactory factory;
    private ParamInfo[] paramInfos;

    public LatticePainterOptions(DrawParameters drawParams) {
        factory = makeDrawStrategiesFactory(drawParams);
    }

    //for testing
    public LatticePainterOptions() {
        this(new DefaultDrawParams());
    }


    protected ModelsFactory makeDrawStrategiesFactory(DrawParameters drawParams) {
        return new conexp.frontend.latticeeditor.drawstrategies.DefaultDrawStrategiesModelsFactory(drawParams);
    }


    public canvas.CanvasColorScheme getColorScheme() {
        return colorScheme;
    }

    public IHighlightStrategy getHighlightStrategy() {
        return getDrawStrategiesContext().getHighlightStrategy();
    }

    public synchronized DrawStrategiesContext getDrawStrategiesContext() {
        return getLatticePainterDrawStrategyContext();
    }

    protected LatticeCanvasDrawStrategiesContext getLatticePainterDrawStrategyContext() {
        if (null == drawStrategiesContext) {
            drawStrategiesContext = new LatticeCanvasDrawStrategiesContext(factory, getPropertyChangeSupport());
        }
        return drawStrategiesContext;
    }

    public Layouter getLayoutStrategy() {
        return getLatticePainterDrawStrategyContext().getLayouter();
    }

    public int getSmallGridSize() {
        return getSmallGridSizeValue().getValue();
    }

    synchronized BoundedIntValue getSmallGridSizeValue() {
        if (null == smallGridSize) {
            smallGridSize = makeBoundedIntValue(SMALL_GRID_SIZE_PROPERTY, 8, 2, 20);
        }
        return smallGridSize;
    }

    private BoundedIntValue makeBoundedIntValue(final String propName, final int initialValue, final int minimalValue, final int maximalValue) {
        BoundedIntValue boundedIntValue = new BoundedIntValue(propName, initialValue, minimalValue, maximalValue);
        boundedIntValue.setPropertyChange(getPropertyChangeSupport());
        boundedIntValue.setVetoPropertyChange(getVetoPropertyChange());
        return boundedIntValue;
    }

    public boolean setFigureDrawingStrategy(String key) {
        return getLatticePainterDrawStrategyContext().getNodeRadiusStrategyItem().setValueByKey(key);
    }

    public int getLabelsFontSize() {
        return getLabelsFontSizeValue().getValue();
    }

    BoundedIntValue labelsFontSize;

    public synchronized BoundedIntValue getLabelsFontSizeValue() {
        if (null == labelsFontSize) {
            labelsFontSize = makeBoundedIntValue(LABELS_FONT_SIZE_PROPERTY, 12, 6, 50);
            labelsFontSize.addPropertyChangeListener(new PropertyChangeListener(){
                public void propertyChange(PropertyChangeEvent evt) {
                    if(LABELS_FONT_SIZE_PROPERTY.equals(evt.getPropertyName())){
                        clearCachedFont();
                    }
                }
            });
        }
        return labelsFontSize;
    }

    private void clearCachedFont() {
        cachedFont=null;
    }


    Font cachedFont = null;

    public Font getLabelsFont(Graphics g) {
        if(cachedFont==null){
            cachedFont = g.getFont().deriveFont((float)getLabelsFontSize());
        }
        return cachedFont;
    }

    public FontMetrics getLabelsFontMetrics(Graphics g) {
        return g.getFontMetrics(getLabelsFont(g));
    }


    public ParamInfo[] getParams() {
        if (null == paramInfos) {
            paramInfos = makeParamInfo();
        }
        return paramInfos;
    }

    private ParamInfo[] makeParamInfo() {
        return new ParamInfo[]{
            new BoundedIntValueParamInfo("Label font size ", getLabelsFontSizeValue())
        };
    }
}


