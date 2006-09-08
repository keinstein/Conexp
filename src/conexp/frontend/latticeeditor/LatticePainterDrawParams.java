/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor;

import conexp.util.gui.paramseditor.BooleanParamInfo;
import conexp.util.gui.paramseditor.BoundedIntValueParamInfo;
import conexp.util.gui.paramseditor.ParamInfo;
import conexp.util.gui.paramseditor.ParamsProvider;
import conexp.util.valuemodels.BooleanValueModel;
import conexp.util.valuemodels.BoundedIntValue;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.beans.VetoableChangeSupport;
import java.util.prefs.Preferences;

public class LatticePainterDrawParams extends BasicDrawParams implements ParamsProvider, DrawParamsProperties {
    private BoundedIntValue maxNodeRadius;
    private BoundedIntValue gridSizeX;
    private BoundedIntValue gridSizeY;
    private BooleanValueModel showCollisions;

    boolean drawConceptNo = false;//true;

    protected transient VetoableChangeSupport vetoPropertyChange;
    protected transient PropertyChangeSupport propertyChange;


    private float maxEdgeStroke = DEFAULT_MAX_EDGE_STROKE;


    public DrawParameters makeCopy() {
        LatticePainterDrawParams ret = new LatticePainterDrawParams();
        ret.maxNodeRadius = ret.makeBoundedIntValue(getMaxNodeRadiusValue());
        ret.gridSizeX = ret.makeBoundedIntValue(getGridSizeXValue());
        ret.gridSizeY = ret.makeBoundedIntValue(getGridSizeYValue());
        ret.showCollisions = ret.makeBooleanValueModel(getShowCollisionsValue());
        ret.drawConceptNo = drawConceptNo;
        ret.maxEdgeStroke = maxEdgeStroke;
        return ret;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof LatticePainterDrawParams)) {
            return false;
        }
        LatticePainterDrawParams other = (LatticePainterDrawParams) obj;
        if (maxNodeRadius == null ? other.maxNodeRadius != null :
                !maxNodeRadius.equals(other.maxNodeRadius)) {
            return false;
        }
        if (gridSizeX == null ? other.gridSizeX != null : !gridSizeX.equals(other.gridSizeX)) {
            return false;
        }
        if (gridSizeY == null ? other.gridSizeY != null : !gridSizeY.equals(other.gridSizeY)) {
            return false;
        }
        if (showCollisions == null ? other.showCollisions != null : !showCollisions.equals(other.showCollisions)) {
            return false;
        }
        if (drawConceptNo != other.drawConceptNo) {
            return false;
        }
        if (maxEdgeStroke != other.maxEdgeStroke) {
            return false;
        }
        return true;
    }


    public int hashCode() {
        int ret = maxNodeRadius.getValue();
        ret = 29 * ret + gridSizeX.getValue() + (drawConceptNo ? 13 : 0);
        return ret;
    }


    public LatticePainterDrawParams() {
        super();
    }

    public LatticePainterDrawParams(PropertyChangeSupport propertyChange, VetoableChangeSupport vetoPropertyChange) {
        super();
        this.propertyChange = propertyChange;
        this.vetoPropertyChange = vetoPropertyChange;
    }

    public int getGridSizeX() {
        return getGridSizeXValue().getValue();
    }


    public synchronized BoundedIntValue getGridSizeXValue() {
        if (null == gridSizeX) {
            gridSizeX = makeBoundedIntValue(GRID_SIZE_X_PROPERTY, DEFAULT_GRID_SIZE_X, MIN_GRID_SIZE_X, MAX_GRID_SIZE_X);
        }
        return gridSizeX;
    }

    public int getGridSizeY() {
        return getGridSizeYValue().getValue();
    }

    public synchronized BoundedIntValue getGridSizeYValue() {
        if (null == gridSizeY) {
            gridSizeY = makeBoundedIntValue(GRID_SIZE_Y_PROPERTY, DEFAULT_GRID_SIZE_Y, MIN_GRID_SIZE_Y, MAX_GRID_SIZE_Y);
        }
        return gridSizeY;
    }


    public float getMaxEdgeStroke() {
        return maxEdgeStroke;
    }

    public int getMaxNodeRadius() {
        return getMaxNodeRadiusValue().getValue();
    }

    public synchronized BoundedIntValue getMaxNodeRadiusValue() {
        if (null == maxNodeRadius) {
            maxNodeRadius = makeBoundedIntValue(MAX_NODE_RADIUS_PROPERTY, DEFAULT_MAX_NODE_RADIUS, MIN_MAX_NODE_RADIUS, MAX_MAX_NODE_RADIUS);
        }
        return maxNodeRadius;
    }


    public int getMinNodeRadius() {
        return getMaxNodeRadiusValue().minVal;
    }


    synchronized BooleanValueModel getShowCollisionsValue() {
        if (null == showCollisions) {
            showCollisions = makeBooleanValueModel(SHOW_COLLISIONS_PROPERTY, DEFAULT_SHOW_COLLISIONS);
        }
        return showCollisions;
    }

    public boolean isShowCollisions() {
        return getShowCollisionsValue().getValue();
    }

    public void setShowCollisions(boolean newValue) {
        getShowCollisionsValue().setValue(newValue);
    }

    protected PropertyChangeSupport getPropertyChange() {
        if (propertyChange == null) {
            propertyChange = new PropertyChangeSupport(this);
        }
        return propertyChange;
    }

    protected VetoableChangeSupport getVetoPropertyChange() {
        if (vetoPropertyChange == null) {
            vetoPropertyChange = new VetoableChangeSupport(this);
        }
        return vetoPropertyChange;
    }

    public void setDrawConceptNo(boolean newValue) {
        drawConceptNo = newValue;
    }

    public boolean isDrawConceptNo() {
        return drawConceptNo;
    }


    private BoundedIntValue makeBoundedIntValue(String name, int value, int lowBound, int upBound) {
        BoundedIntValue ret = new BoundedIntValue(name, value, lowBound, upBound);
        ret.setPropertyChange(getPropertyChange());
        ret.setVetoPropertyChange(getVetoPropertyChange());
        return ret;
    }

    private BoundedIntValue makeBoundedIntValue(BoundedIntValue valueModel) {
        return makeBoundedIntValue(valueModel.getPropertyName(), valueModel.getValue(),
                valueModel.minVal, valueModel.maxVal);
    }

    private BooleanValueModel makeBooleanValueModel(String name, boolean value) {
        BooleanValueModel ret = new BooleanValueModel(name, value);
        ret.setPropertyChange(getPropertyChange());
        return ret;
    }

    private BooleanValueModel makeBooleanValueModel(BooleanValueModel valueModel) {
        return makeBooleanValueModel(valueModel.getPropertyName(), valueModel.getValue());
    }

    public void setGridSizeX(int newGridSizeX) throws PropertyVetoException {
        getGridSizeXValue().setValue(newGridSizeX);
    }

    public void setGridSizeY(int newGridSizeY) throws PropertyVetoException {
        getGridSizeYValue().setValue(newGridSizeY);
    }

    public void setMaxEdgeStroke(float newMaxEdgeStroke) {
        maxEdgeStroke = newMaxEdgeStroke;
    }

    public void setNodeMaxRadius(int newMaxNodeRadius) throws PropertyVetoException {
        getMaxNodeRadiusValue().setValue(newMaxNodeRadius);
    }

    private transient ParamInfo[] params;


    public ParamInfo[] getParams() {
        if (null == params) {
            params = new ParamInfo[]{
                    new BoundedIntValueParamInfo("Grid Size X", getGridSizeXValue()),
                    new BoundedIntValueParamInfo("Grid Size Y", getGridSizeYValue()),
                    new BoundedIntValueParamInfo("Node radius", getMaxNodeRadiusValue()),
                    new BooleanParamInfo("Show collisions", getShowCollisionsValue())
            };
        }
        return params;
    }

    transient Preferences preferences = Preferences.userNodeForPackage(LatticeCanvasDrawStrategiesContext.class);

    public void setPreferences(Preferences newPreferences) {
        this.preferences = newPreferences;
    }


    public Preferences getPreferences() {
        return preferences;
    }

    public void doStorePreferences() {
        getPreferences().putInt(GRID_SIZE_X_PROPERTY, getGridSizeX());
        getPreferences().putInt(GRID_SIZE_Y_PROPERTY, getGridSizeY());
        getPreferences().putInt(MAX_NODE_RADIUS_PROPERTY, getMaxNodeRadius());
        getPreferences().putBoolean(SHOW_COLLISIONS_PROPERTY, isShowCollisions());
    }

    public void doRestorePreferences(Preferences preferences) throws PropertyVetoException {
        getGridSizeXValue().setValue(preferences.getInt(GRID_SIZE_X_PROPERTY, DEFAULT_GRID_SIZE_X));
        getGridSizeYValue().setValue(preferences.getInt(GRID_SIZE_Y_PROPERTY, DEFAULT_GRID_SIZE_Y));
        getMaxNodeRadiusValue().setValue(preferences.getInt(MAX_NODE_RADIUS_PROPERTY, DEFAULT_MAX_NODE_RADIUS));
        getShowCollisionsValue().setValue(preferences.getBoolean(SHOW_COLLISIONS_PROPERTY, DEFAULT_SHOW_COLLISIONS));
    }

    public void restorePreferences() {
        try {
            doRestorePreferences(getPreferences());
        } catch (PropertyVetoException e) {
            //ignored
        }
    }


    public synchronized void addPropertyChangeListener(PropertyChangeListener listener) {
        getPropertyChange().addPropertyChangeListener(listener);
    }

    public synchronized void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        getPropertyChange().addPropertyChangeListener(propertyName, listener);
    }

    public synchronized void addVetoableChangeListener(VetoableChangeListener listener) {
        getVetoPropertyChange().addVetoableChangeListener(listener);
    }

    public synchronized void addVetoableChangeListener(String propertyName, VetoableChangeListener listener) {
        getVetoPropertyChange().addVetoableChangeListener(propertyName, listener);
    }


    public synchronized void removePropertyChangeListener(PropertyChangeListener listener) {
        getPropertyChange().removePropertyChangeListener(listener);
    }

    public synchronized void removeVetoableChangeListener(VetoableChangeListener listener) {
        getVetoPropertyChange().removeVetoableChangeListener(listener);
    }


    public String toString() {
        return "LatticePainterDrawParams{" +
                "maxNodeRadius=" + maxNodeRadius +
                ", gridSizeX=" + gridSizeX +
                ", gridSizeY=" + gridSizeY +
                ", drawConceptNo=" + drawConceptNo +
                ", maxEdgeStroke=" + maxEdgeStroke +
                ", showCollisions=" + showCollisions +
                '}';
    }
}
