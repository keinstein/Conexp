/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.core.layoutengines;

import conexp.core.Lattice;
import conexp.core.layout.Layouter;
import conexp.frontend.latticeeditor.DrawParameters;
import util.Assert;

public class SimpleLayoutEngine extends LayoutEngineBase {
    public void shutdown() {
    }

    protected void doRestartLayout(Lattice lattice, DrawParameters parameters) {
        doStartLayout(lattice, parameters);
    }

    protected void doStartLayout(Lattice lattice, DrawParameters parameters) {
        Layouter layouter = getLayoter();
        if (layouter.isIncremental()) {
            Assert.isTrue(false, "Simple layout engine doesn't support incremental layouters");
            return;
        }

        layouter.addLayoutChangeListener(layoutChangeListener);
        try {
            layouter.initLayout(lattice, parameters);
            layouter.performLayout();
        } finally {
            layouter.removeLayoutChangeListener(layoutChangeListener);
        }
    }
}
