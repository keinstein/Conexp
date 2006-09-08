/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.layoutengines;

import conexp.core.Lattice;
import conexp.core.layout.LayoutParameters;
import conexp.core.layout.Layouter;

class LayoutEvent {
    final static int START_LAYOUT = 1;
    final static int RESTART_LAYOUT = 2;

    public final Lattice lattice;
    public final LayoutParameters drawParams;
    public final Layouter layouter;
    public final int command;

    LayoutEvent(Lattice lattice, LayoutParameters drawParams, Layouter layouter, int command) {
        this.lattice = lattice;
        this.layouter = layouter;
        this.command = command;
        this.drawParams = drawParams;
    }
}
