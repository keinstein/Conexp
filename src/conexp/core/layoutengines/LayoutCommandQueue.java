/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/

package conexp.core.layoutengines;


class LayoutCommandQueue {
    protected java.lang.Object syncLock = new Object();
    protected LayoutEvent startLayout;
    protected LayoutEvent restartLayout;
    protected int waitingForTake = 0;


    protected synchronized LayoutEvent extract() {
        LayoutEvent x = startLayout;
        if (x != null) {
            startLayout = null;
            return x;
        }
        x = restartLayout;
        restartLayout = null;
        return x;
    }


    public synchronized boolean isEmpty() {
        return startLayout == null && restartLayout == null;
    }


    public void put(LayoutEvent evt) throws InterruptedException {
        util.Assert.isTrue(null != evt);
        if (Thread.interrupted()) throw new InterruptedException();
        synchronized (this) {
            com.visibleworkings.trace.Trace.gui.debugm("processing post command");
            switch (evt.command) {
                case LayoutEvent.START_LAYOUT:
                    startLayout = evt;
                    restartLayout = null;
                    break;
                case LayoutEvent.RESTART_LAYOUT:
                    restartLayout = evt;
                    break;
                default :
                    util.Assert.isTrue(false);
            }
            if (waitingForTake > 0) {
                notify();
            }
        }
    }


    public LayoutEvent take() throws InterruptedException {
        LayoutEvent evt = extract();
        if (evt != null) {
            return evt;
        }
        synchronized (this) {
            try {
                waitingForTake++;
                try {
                    for (; ;) {
                        evt = extract();
                        if (evt != null) {
                            return evt;
                        }
                        wait();
                    }
                } finally {
                    waitingForTake--;
                }
            } catch (InterruptedException ex) {
                notify();
                throw ex;
            }
        }
    }
}
