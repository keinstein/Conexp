/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.layoutengines;

import com.visibleworkings.trace.Trace;
import util.Assert;


class LayoutCommandQueue {
    private LayoutEvent startLayout;
    private LayoutEvent restartLayout;
    private int waitingForTake = 0;


    private synchronized LayoutEvent extract() {
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
        Assert.isTrue(null != evt);
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        synchronized (this) {
            Trace.gui.debugm("processing post command");
            switch (evt.command) {
                case LayoutEvent.START_LAYOUT:
                    startLayout = evt;
                    restartLayout = null;
                    break;
                case LayoutEvent.RESTART_LAYOUT:
                    restartLayout = evt;
                    break;
                default :
                    Assert.isTrue(false);
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
