package conexp.frontend.latticeeditor;

import canvas.IHighlightStrategy;
import conexp.core.layout.Layouter;
import conexp.util.valuemodels.BoundedIntValue;
import util.BaseVetoablePropertyChangeSupplier;

public class LatticePainterOptions extends BaseVetoablePropertyChangeSupplier implements LatticeCanvasScheme {
    private BoundedIntValue smallGridSize;


    private LatticeCanvasDrawStrategiesContext drawStrategiesContext;
    private DrawParameters drawParams;

    private canvas.CanvasColorScheme colorScheme = new canvas.DefaultColorScheme();
    private ModelsFactory factory = makeDrawStrategiesFactory();

    public LatticePainterOptions() {
        super();
    }

    public canvas.CanvasColorScheme getColorScheme() {
        return colorScheme;
    }

    public synchronized DrawParameters getDrawParams() {
        if (null == drawParams) {
            drawParams = new LatticePainterDrawParams(getPropertyChangeSupport(), getVetoPropertyChange());
        }
        return drawParams;
    }

    LatticePainterDrawParams getEditableDrawParams() {
        return (LatticePainterDrawParams) getDrawParams();
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

    private ModelsFactory makeDrawStrategiesFactory() {
        return new conexp.frontend.latticeeditor.drawstrategies.DefaultDrawStrategiesModelsFactory(getDrawParams());
    }

    public void setFigureDrawingStrategy(String key) {
        getLatticePainterDrawStrategyContext().getNodeRadiusStrategyItem().setValueByKey(key);
    }
}