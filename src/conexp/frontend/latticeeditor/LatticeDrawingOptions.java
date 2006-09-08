/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor;

import util.BaseVetoablePropertyChangeSupplier;

public class LatticeDrawingOptions extends BaseVetoablePropertyChangeSupplier implements DrawParamsProvider {
    private DrawParameters drawParams;

    public LatticeDrawingOptions() {
        super();
    }

/*
    public void setDrawParams(DrawParameters drawParams) {
        this.drawParams = drawParams;
    }

*/

    public synchronized DrawParameters getDrawParams() {
        if (null == drawParams) {
            drawParams = new LatticePainterDrawParams(getPropertyChangeSupport(), getVetoPropertyChange());
        }
        return drawParams;
    }

    public LatticePainterDrawParams getEditableDrawingOptions() {
        return (LatticePainterDrawParams) getDrawParams();
    }

}
