/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.layout;

import com.visibleworkings.trace.Trace;
import conexp.core.ItemSet;
import conexp.core.Lattice;
import conexp.util.gui.paramseditor.ParamInfo;
import util.Assert;
import util.StringUtil;

import java.awt.geom.Point2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;


public abstract class GenericLayouter implements Layouter {
    protected Lattice lattice;
    protected LayoutParameters drawParams;

    protected static class LayoutConceptInfo {
        protected double x;
        public double y;

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
        }

        public void setY(double y) {
            this.y = y;
        }

        public double getY() {
            return y;
        }
    }

    protected LayoutConceptInfo[] elementMap;

    protected PropertyChangeSupport layoutChange;
    private ParamInfo[] params;

    protected GenericLayouter() {
        super();
    }

    public void calcInitialPlacement() {

    }

    public void improveOnce() {

    }

    public void initLayout(Lattice l, LayoutParameters drawParams) {
        Assert.isTrue(l.isValid());
        setLayoutParams(l, drawParams);
        initMapping();
        afterInitLayout();
    }

    protected synchronized void initMapping() {
        int bound = lattice.conceptsCount();
        elementMap = new LayoutConceptInfo[bound];
        for (int i = bound; --i >= 0;) {
            elementMap[i] = makeConceptInfo();
        }
    }

    protected LayoutConceptInfo makeConceptInfo() {
        return new LayoutConceptInfo();
    }

    protected LayoutConceptInfo getLayoutConceptInfo(ItemSet c) {
        return elementMap[c.getIndex()];
    }

    public abstract void performLayout();

    protected void setLayoutParams(Lattice lat, LayoutParameters drawParams) {
        this.lattice = lat;
        this.drawParams = drawParams;
    }

    public void addLayoutChangeListener(PropertyChangeListener listener) {
        getLayoutChange().addPropertyChangeListener(listener);
    }

    protected void afterInitLayout() {
    }

    protected void fireLayoutChanged() {
        try {
            fireLayoutPropertyChanged(LAYOUT_CHANGE);
        } catch (Throwable e) {
            Trace.trace.errorm(StringUtil.stackTraceToString(e));
        }
    }

    protected LayoutParameters getDrawParams() {
        return drawParams;
    }

    protected synchronized PropertyChangeSupport getLayoutChange() {
        if (null == layoutChange) {
            layoutChange = new PropertyChangeSupport(this);
        }
        return layoutChange;
    }

    public synchronized ParamInfo[] getParams() {
        if (null == params) {
            params = makeParams();
        }
        return params;
    }

    protected ParamInfo[] makeParams() {
        return new ParamInfo[0];
    }

    public void removeLayoutChangeListener(PropertyChangeListener listener) {
        getLayoutChange().removePropertyChangeListener(listener);
    }

    public void setLayoutChange(PropertyChangeSupport newLayoutChange) {
        layoutChange = newLayoutChange;
    }

    abstract protected void assignCoordsToLattice();

    public void setCoordsForConcept(ItemSet c, Point2D coords) {
        LayoutConceptInfo info = getLayoutConceptInfo(c);
        coords.setLocation(info.x, info.y);
    }


    protected void fireLayoutPropertyChanged(String propertyName) {
        getLayoutChange().firePropertyChange(new PropertyChangeEvent(this, propertyName, null, null));
    }

    /**
     * Generic equals for layouters
     * the main value of equals is for the comparing whether layouters
     * are of the same class and parameters
     *
     * @param obj
     * @return
     */
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof GenericLayouter)) {
            return false;
        }
        GenericLayouter other = (GenericLayouter) obj;
        if (!getClass().equals(other.getClass())) {
            return false;
        }
        if (drawParams == null ? other.drawParams != null : !drawParams.equals(other.drawParams)) {
            return false;
        }

        return true;
    }

    public int hashCode() {
        return 29 * getClass().hashCode() + (drawParams == null ? 0 : drawParams.hashCode());
    }


}
