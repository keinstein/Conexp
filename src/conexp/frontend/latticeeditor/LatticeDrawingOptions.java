/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/

package conexp.frontend.latticeeditor;

import util.BaseVetoablePropertyChangeSupplier;

public class LatticeDrawingOptions extends BaseVetoablePropertyChangeSupplier implements LatticeDrawingSchema {
    PainterOptionsPaneEditor optionsEditor;

    private DrawParameters drawParams;

    public LatticeDrawingOptions() {
        super();
    }

    public void setDrawParams(DrawParameters drawParams) {
        this.drawParams = drawParams;
    }

    public synchronized DrawParameters getDrawParams() {
        if (null == drawParams) {
            drawParams = new LatticePainterDrawParams(getPropertyChangeSupport(), getVetoPropertyChange());
        }
        return drawParams;
    }
}
