/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.layoutengines;

import com.visibleworkings.trace.Trace;
import conexp.core.Lattice;
import conexp.core.layout.LayoutParameters;

public class ThreadedLayoutEngine extends LayoutEngineBase {
    private LayoutImproveThread layoutThread = null;

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

    private synchronized LayoutImproveThread getLayoutThread() {
        if (null == layoutThread) {
            layoutThread = new LayoutImproveThread(layoutChangeListener);
            layoutThread.start();
        }
        return layoutThread;
    }

    protected void doStartLayout(Lattice lattice, LayoutParameters drawParameters) {
        getLayoutThread().postEvent(new LayoutEvent(lattice, drawParameters, getLayoter(), LayoutEvent.START_LAYOUT));
    }

    protected void doRestartLayout(Lattice lattice, LayoutParameters parameters) {
        getLayoutThread().postEvent(new LayoutEvent(lattice, parameters, getLayoter(), LayoutEvent.RESTART_LAYOUT));

    }


    public LayoutEngine newInstance() {
        return new ThreadedLayoutEngine();  //To change body of implemented methods use File | Settings | File Templates.
    }

}
