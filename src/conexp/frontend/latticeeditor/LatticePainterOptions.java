/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.latticeeditor;

import canvas.IHighlightStrategy;
import conexp.core.layout.Layouter;
import conexp.util.valuemodels.BoundedIntValue;
import util.BaseVetoablePropertyChangeSupplier;

//todo: rename to LatticeCanvasSchemeWithOptions
public class LatticePainterOptions extends BaseVetoablePropertyChangeSupplier implements LatticeCanvasScheme {
    private BoundedIntValue smallGridSize;


    private LatticeCanvasDrawStrategiesContext drawStrategiesContext;

    private canvas.CanvasColorScheme colorScheme = new canvas.DefaultColorScheme();

    private ModelsFactory factory;

    public LatticePainterOptions(DrawParameters drawParams){
        factory = makeDrawStrategiesFactory(drawParams);
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
            smallGridSize = new BoundedIntValue("smallGridSize", 8, 2, 20);
            smallGridSize.setPropertyChange(getPropertyChangeSupport());
            smallGridSize.setVetoPropertyChange(getVetoPropertyChange());
        }
        return smallGridSize;
    }

    public void setFigureDrawingStrategy(String key) {
        getLatticePainterDrawStrategyContext().getNodeRadiusStrategyItem().setValueByKey(key);
    }
}
