package conexp.core.layoutengines;

import conexp.core.Lattice;
import conexp.core.layout.Layouter;
import conexp.frontend.latticeeditor.DrawParameters;

class LayoutEvent {
    final static int START_LAYOUT = 1; // layouter may be old, lattice is new
    final static int RESTART_LAYOUT = 2;

    public final Lattice lattice;
    public final DrawParameters drawParams;
    public final Layouter layouter;
    public final int command;

    LayoutEvent(Lattice lattice, DrawParameters drawParams, Layouter layouter, int command) {
        this.lattice = lattice;
        this.layouter = layouter;
        this.command = command;
        this.drawParams = drawParams;
    }
}