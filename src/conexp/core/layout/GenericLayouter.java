package conexp.core.layout;

import com.visibleworkings.trace.Trace;
import conexp.core.ItemSet;
import conexp.frontend.latticeeditor.DrawParameters;
import conexp.util.gui.paramseditor.ParamInfo;
import util.StringUtil;

import java.awt.geom.Point2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Insert the type's description here.
 * Creation date: (03.03.01 20:21:44)
 * @author
 */
public abstract class GenericLayouter implements conexp.core.layout.Layouter {
    protected conexp.core.Lattice lattice;
    protected DrawParameters drawParams;

    protected static class LayoutConceptInfo {
        public double x;
        public double y;
    }

    protected LayoutConceptInfo[] elementMap;

    protected PropertyChangeSupport layoutChange;
    private conexp.util.gui.paramseditor.ParamInfo[] params;

    public GenericLayouter() {
        super();
    }

    public void calcInitialPlacement() {

    }

    public void improveOnce() {

    }

    public void initLayout(conexp.core.Lattice l, DrawParameters drawParams) {
        util.Assert.isTrue(l.isValid());
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

    protected void setLayoutParams(conexp.core.Lattice lat, DrawParameters drawParams) {
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

    protected DrawParameters getDrawParams() {
        return drawParams;
    }

    protected synchronized java.beans.PropertyChangeSupport getLayoutChange() {
        if (null == layoutChange) {
            layoutChange = new PropertyChangeSupport(this);
        }
        return layoutChange;
    }

    public synchronized conexp.util.gui.paramseditor.ParamInfo[] getParams() {
        if (null == params) {
            params = makeParams();
        }
        return params;
    }

    protected ParamInfo[] makeParams() {
        return new ParamInfo[0];
    }

    public void removeLayoutChangeListener(java.beans.PropertyChangeListener listener) {
        getLayoutChange().removePropertyChangeListener(listener);
    }

    public void setLayoutChange(java.beans.PropertyChangeSupport newLayoutChange) {
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
}