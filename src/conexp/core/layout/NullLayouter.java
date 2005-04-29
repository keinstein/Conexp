/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.core.layout;

import conexp.core.ItemSet;
import conexp.core.Lattice;
import conexp.util.gui.paramseditor.ParamInfo;

import java.awt.geom.Point2D;
import java.beans.PropertyChangeListener;

public class NullLayouter extends NonIncrementalLayouter {

    public void addLayoutChangeListener(PropertyChangeListener listener) {
    }

    public void removeLayoutChangeListener(PropertyChangeListener listener) {
    }

    public ParamInfo[] getParams() {
        return new ParamInfo[0];
    }

    public void initLayout(Lattice l, LayoutParameters drawParams) {
    }

//calcInitialPlacement and improveOnce are already empty in GenericLayouter
    public void performLayout() {
    }

    public void setCoordsForConcept(ItemSet c, Point2D coords) {
    }

    protected void assignCoordsToLattice() {
    }
}
