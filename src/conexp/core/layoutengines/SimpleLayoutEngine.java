/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.layoutengines;

import conexp.core.Lattice;
import conexp.core.layout.LayoutParameters;
import conexp.core.layout.Layouter;
import util.Assert;

public class SimpleLayoutEngine extends LayoutEngineBase {
    public void shutdown() {
    }


    public LayoutEngine newInstance() {
        return new SimpleLayoutEngine();  //To change body of implemented methods use File | Settings | File Templates.
    }

    protected void doRestartLayout(Lattice lattice, LayoutParameters parameters) {
        doStartLayout(lattice, parameters);
    }

    protected void doStartLayout(Lattice lattice, LayoutParameters parameters) {
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
