/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.layoutengines;

import com.visibleworkings.trace.Trace;
import conexp.core.Lattice;
import conexp.core.layout.LayoutParameters;
import conexp.core.layout.Layouter;

import java.beans.PropertyChangeListener;

class LayoutImproveThread extends Thread {
    private LayoutCommandQueue inputQueue;
    private PropertyChangeListener layoutListener;
    private Layouter currentLayouter;

    private void setCurrentLayouter(Layouter layouter) {
        if (currentLayouter != layouter) {
            removeLayoutChangeListener();
            currentLayouter = layouter;
            currentLayouter.addLayoutChangeListener(layoutListener);
        }
    }

    public void shutDown() {
        removeLayoutChangeListener();
    }

    private void performLayout(Lattice lat, LayoutParameters drawParams) throws InterruptedException {
        currentLayouter.initLayout(lat, drawParams);
        doRealLayout();
    }

    private void doRealLayout() throws InterruptedException {
        Trace.gui.debugm("inside do real layout");
        if (!currentLayouter.isIncremental()) {
            currentLayouter.performLayout();
        } else {
            currentLayouter.calcInitialPlacement();
            while (!currentLayouter.isDone()) {
                if (!inputQueue.isEmpty()) {
                    break;
                }
                currentLayouter.improveOnce();
                sleep(50);
            }
        }
    }

    public void run() {
        try {
            for (; ;) {
                Trace.gui.debugm("waiting for layout command");
                LayoutEvent evt = inputQueue.take();
                Trace.gui.debugm("start processing layout command");
                switch (evt.command) {
                    case LayoutEvent.START_LAYOUT:
                        setCurrentLayouter(evt.layouter);
                        performLayout(evt.lattice, evt.drawParams);
                        break;
                    case LayoutEvent.RESTART_LAYOUT:
                        doRealLayout();
                        break;
                }
            }
        } catch (InterruptedException ex) {
            return;
        }
    }


    public LayoutImproveThread(PropertyChangeListener layoutChangeListener) {
        super();
        this.layoutListener = layoutChangeListener;
        inputQueue = new LayoutCommandQueue();
        setPriority(Thread.MIN_PRIORITY);
    }

    public void postEvent(LayoutEvent evt) {
        try {
            inputQueue.put(evt);
        } catch (InterruptedException ex) {

            this.interrupt();
        }
    }

    private void removeLayoutChangeListener() {
        if (null != currentLayouter) {
            currentLayouter.removeLayoutChangeListener(layoutListener);
        }
    }
}
