/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor;

import canvas.CanvasColorScheme;
import canvas.CanvasScheme;
import canvas.DefaultColorScheme;
import canvas.IHighlightStrategy;
import conexp.core.layout.Layouter;
import conexp.frontend.latticeeditor.drawstrategies.DefaultDrawStrategiesModelsFactory;
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
import java.beans.PropertyVetoException;
import java.util.prefs.Preferences;


public class LatticePainterOptions extends BaseVetoablePropertyChangeSupplier implements LatticeCanvasScheme, ParamsProvider, LatticeCanvasSchemeProperties {
    private BoundedIntValue smallGridSize;

    private LatticeCanvasDrawStrategiesContext drawStrategiesContext;

    private CanvasColorScheme colorScheme = new DefaultColorScheme();

    private DrawStrategiesModelsFactory factory;
    private ParamInfo[] paramInfos;
    public static final int DEFAULT_LABEL_FONT_SIZE = 12;

    public LatticePainterOptions(DrawParameters drawParams) {
        factory = makeDrawStrategiesFactory(drawParams);
    }

    private LatticePainterOptions(final DrawStrategiesModelsFactory modelsFactory) {
        factory = modelsFactory;
    }

    //for testing
    public LatticePainterOptions() {
        this(BasicDrawParams.getInstance());
    }

    protected static DrawStrategiesModelsFactory makeDrawStrategiesFactory(DrawParameters drawParams) {
        return new DefaultDrawStrategiesModelsFactory(drawParams);
    }

    public CanvasColorScheme getColorScheme() {
        return colorScheme;
    }

    public IHighlightStrategy getHighlightStrategy() {
        return getDrawStrategiesContext().getHighlighter();
    }

    public synchronized DrawStrategiesContext getDrawStrategiesContext() {
        return getLatticePainterDrawStrategyContext();
    }

    public LatticeCanvasDrawStrategiesContext getLatticePainterDrawStrategyContext() {
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

    private BoundedIntValue makeBoundedIntValue(BoundedIntValue value) {
        return makeBoundedIntValue(value.getPropertyName(), value.getValue(), value.minVal, value.maxVal);
    }

    public boolean setFigureDrawingStrategy(String key) {
        return getLatticePainterDrawStrategyContext().getNodeRadiusStrategyItem().setValueByKey(key);
    }

    Preferences preferences = Preferences.userNodeForPackage(LatticePainterOptions.class);

    public void setPreferences(Preferences preferences) {
        this.preferences = preferences;
    }

    public Preferences getPreferences() {
        return preferences;
    }


    public void doStorePreferences() {
        getPreferences().putInt(LABELS_FONT_SIZE_PROPERTY, getLabelsFontSizeValue().getValue());
        getLatticePainterDrawStrategyContext().doStorePreferences();
    }

    public void restorePreferences() {
        try {
            getLabelsFontSizeValue().setValue(getPreferences().getInt(LABELS_FONT_SIZE_PROPERTY, DEFAULT_LABEL_FONT_SIZE));
        } catch (PropertyVetoException e) {
            //would be suppressed
        }
        getLatticePainterDrawStrategyContext().restorePreferences();
    }

    public int getLabelsFontSize() {
        return getLabelsFontSizeValue().getValue();
    }

    BoundedIntValue labelsFontSize;

    public synchronized BoundedIntValue getLabelsFontSizeValue() {
        if (null == labelsFontSize) {
            labelsFontSize = makeBoundedIntValue(LABELS_FONT_SIZE_PROPERTY, DEFAULT_LABEL_FONT_SIZE, 6, 50);
            labelsFontSize.addPropertyChangeListener(new PropertyChangeListener() {
                public void propertyChange(PropertyChangeEvent evt) {
                    if (LABELS_FONT_SIZE_PROPERTY.equals(evt.getPropertyName())) {
                        clearCachedFont();
                    }
                }
            });
        }
        return labelsFontSize;
    }

    private void clearCachedFont() {
        cachedFont = null;
    }

    transient Font cachedFont = null;

    public Font getLabelsFont(Graphics g) {
        if (cachedFont == null) {
            cachedFont = g.getFont().deriveFont((float) getLabelsFontSize());
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

    public String toString() {
        return "LatticePainterOptions{" +
                "smallGridSize=" + smallGridSize +
                ", drawStrategiesContext=" + drawStrategiesContext +
                ", labelsFontSize=" + labelsFontSize +
                '}';
    }


    public boolean isEqual(CanvasScheme other) {
        if (other instanceof LatticePainterOptions) {
            return isEqualByContent((LatticePainterOptions) other);
        }
        return false;
    }

    public boolean isEqualByContent(LatticePainterOptions other) {
        if (!colorScheme.equals(other.colorScheme)) {
            return false;
        }
        if (labelsFontSize == null ?
                other.labelsFontSize != null :
                !labelsFontSize.equals(other.labelsFontSize)) {
            return false;
        }
        if (smallGridSize == null ?
                other.smallGridSize != null :
                !smallGridSize.equals(other.smallGridSize)) {
            return false;
        }
        if (labelsFontSize == null ?
                other.labelsFontSize != null :
                !labelsFontSize.equals(other.labelsFontSize)) {
            return false;
        }
        if (drawStrategiesContext == null ?
                other.drawStrategiesContext != null :
                !drawStrategiesContext.equals(other.drawStrategiesContext)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return 29 * colorScheme.hashCode() +
                (drawStrategiesContext != null ? drawStrategiesContext.hashCode() : 0);
    }

    public CanvasScheme makeCopy() {
        LatticePainterOptions ret = new LatticePainterOptions(factory.makeCopy());
        synchronized (ret) {
            ret.colorScheme = colorScheme.makeCopy();
            if (labelsFontSize != null) {
                ret.labelsFontSize = ret.makeBoundedIntValue(getLabelsFontSizeValue());
            }
            if (smallGridSize != null) {
                ret.smallGridSize = ret.makeBoundedIntValue(getSmallGridSizeValue());
            }
            if (drawStrategiesContext != null) {
                ret.drawStrategiesContext = drawStrategiesContext.makeNativeCopy(ret.getPropertyChangeSupport());
            }

        }
        return ret;

    }
}


