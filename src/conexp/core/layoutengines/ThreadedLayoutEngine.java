/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.core.layoutengines;

import com.visibleworkings.trace.Trace;
import conexp.core.Lattice;
import conexp.frontend.latticeeditor.DrawParameters;

public class ThreadedLayoutEngine extends LayoutEngineBase {
    protected LayoutImproveThread layoutThread = null;

    public void shutdown() {
        shutdownThread();
    }

    private synchronized void shutdownThread() {
        if (null != layoutThread) {
            Trace.gui.debugm("Shutdowning layout thread");
            layoutThread.interrupt();
            layoutThread = null;
        }
    }

    protected synchronized LayoutImproveThread getLayoutThread() {
        if (null == layoutThread) {
            layoutThread = new LayoutImproveThread(layoutChangeListener);
            layoutThread.start();
        }
        return layoutThread;
    }

    protected void doStartLayout(Lattice lattice, DrawParameters drawParameters) {
        getLayoutThread().postEvent(new LayoutEvent(lattice, drawParameters, getLayoter(), LayoutEvent.START_LAYOUT));
    }

    protected void doRestartLayout(Lattice lattice, DrawParameters parameters) {
        getLayoutThread().postEvent(new LayoutEvent(lattice, parameters, getLayoter(), LayoutEvent.RESTART_LAYOUT));

    }


}
