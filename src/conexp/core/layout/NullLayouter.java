/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/

package conexp.core.layout;

import conexp.core.ItemSet;
import conexp.frontend.latticeeditor.DrawParameters;

import java.awt.geom.Point2D;

public class NullLayouter extends NonIncrementalLayouter {

    public void addLayoutChangeListener(java.beans.PropertyChangeListener listener) {
    }

    public void removeLayoutChangeListener(java.beans.PropertyChangeListener listener) {
    }

    public conexp.util.gui.paramseditor.ParamInfo[] getParams() {
        return new conexp.util.gui.paramseditor.ParamInfo[0];
    }

    public void initLayout(conexp.core.Lattice l, DrawParameters drawParams) {
    }

//calcInitialPlacement and improveOnce are already empty in GenericLayouter
    public void performLayout() {
    }

    public void setCoordsForConcept(ItemSet c, Point2D coords) {
    }

    protected void assignCoordsToLattice() {
    }
}
