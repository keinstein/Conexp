/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/

package conexp.frontend.latticeeditor;

import conexp.util.gui.paramseditor.BoundedIntValueParamInfo;
import conexp.util.gui.paramseditor.ParamInfo;
import conexp.util.gui.paramseditor.ParamsProvider;
import conexp.util.valuemodels.BoundedIntValue;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.VetoableChangeListener;
import java.beans.VetoableChangeSupport;

public class LatticePainterDrawParams extends BasicDrawParams implements ParamsProvider {
    private BoundedIntValue maxNodeRadius;
    private BoundedIntValue gridSizeX;
    private BoundedIntValue gridSizeY;

    boolean drawConceptNo = false;//true;

    PainterOptionsPaneEditor optionsEditor;

    protected transient VetoableChangeSupport vetoPropertyChange;
    protected transient PropertyChangeSupport propertyChange;


    private float maxEdgeStroke = DEFAULT_MAX_EDGE_STROKE;


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

    synchronized BoundedIntValue getGridSizeXValue() {
        if (null == gridSizeX) {
            gridSizeX = makeBoundedIntValue("gridSizeX", DEFAULT_GRID_SIZE_X, MIN_GRID_SIZE_X, MAX_GRID_SIZE_X);
        }
        return gridSizeX;
    }

    public int getGridSizeY() {
        return getGridSizeYValue().getValue();
    }

    synchronized BoundedIntValue getGridSizeYValue() {
        if (null == gridSizeY) {
            gridSizeY = makeBoundedIntValue("gridSizeY", DEFAULT_GRID_SIZE_Y, MIN_GRID_SIZE_Y, MAX_GRID_SIZE_Y);
        }
        return gridSizeY;
    }


    public float getMaxEdgeStroke() {
        return maxEdgeStroke;
    }

    public int getMaxNodeRadius() {
        return getMaxNodeRadiusValue().getValue();
    }

    synchronized BoundedIntValue getMaxNodeRadiusValue() {
        if (null == maxNodeRadius) {
            maxNodeRadius = makeBoundedIntValue("maxNodeRadius", DEFAULT_MAX_NODE_RADIUS, MIN_MAX_NODE_RADIUS, MAX_MAX_NODE_RADIUS);
        }
        return maxNodeRadius;
    }


    public int getMinNodeRadius() {
        return getMaxNodeRadiusValue().minVal;
    }

    protected PropertyChangeSupport getPropertyChange() {
        if (propertyChange == null) {
            propertyChange = new java.beans.PropertyChangeSupport(this);
        }
        return propertyChange;
    }

    protected VetoableChangeSupport getVetoPropertyChange() {
        if (vetoPropertyChange == null) {
            vetoPropertyChange = new java.beans.VetoableChangeSupport(this);
        }
        return vetoPropertyChange;
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

    public void setGridSizeX(int newGridSizeX) throws java.beans.PropertyVetoException {
        getGridSizeXValue().setValue(newGridSizeX);
    }

    public void setGridSizeY(int newGridSizeY) throws java.beans.PropertyVetoException {
        getGridSizeYValue().setValue(newGridSizeY);
    }

    public void setMaxEdgeStroke(float newMaxEdgeStroke) {
        maxEdgeStroke = newMaxEdgeStroke;
    }

    public void setNodeMaxRadius(int newMaxNodeRadius) throws java.beans.PropertyVetoException {
        getMaxNodeRadiusValue().setValue(newMaxNodeRadius);
    }

    private ParamInfo[] params;

    public ParamInfo[] getParams() {
        if (null == params) {
            params = new ParamInfo[]{
                new BoundedIntValueParamInfo("Grid Size X", getGridSizeXValue()),
                new BoundedIntValueParamInfo("Grid Size Y", getGridSizeYValue()),
                new BoundedIntValueParamInfo("Node radius", getMaxNodeRadiusValue())
            };
        }
        return params;
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

}
