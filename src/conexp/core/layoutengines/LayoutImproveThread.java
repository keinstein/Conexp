package conexp.core.layoutengines;

import conexp.core.Lattice;
import conexp.core.layout.Layouter;
import conexp.frontend.latticeeditor.DrawParameters;

import java.beans.PropertyChangeListener;

class LayoutImproveThread extends Thread {
    protected LayoutCommandQueue inputQueue;
    protected PropertyChangeListener layoutListener;
    private Layouter currentLayouter;

    protected void setCurrentLayouter(Layouter layouter) {
        if (currentLayouter != layouter) {
            removeLayoutChangeListener();
            currentLayouter = layouter;
            currentLayouter.addLayoutChangeListener(layoutListener);
        }
    }

    public void shutDown() {
        removeLayoutChangeListener();
    }

    protected void performLayout(Lattice lat, DrawParameters drawParams) throws InterruptedException {
        currentLayouter.initLayout(lat, drawParams);
        doRealLayout();
    }

    protected void doRealLayout() throws InterruptedException {
        com.visibleworkings.trace.Trace.gui.debugm("inside do real layout");
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
                com.visibleworkings.trace.Trace.gui.debugm("waiting for layout command");
                LayoutEvent evt = inputQueue.take();
                com.visibleworkings.trace.Trace.gui.debugm("start processing layout command");
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
            //TODO check validity of this operation
            this.interrupt();
        }
    }

    protected void removeLayoutChangeListener() {
        if (null != currentLayouter) {
            currentLayouter.removeLayoutChangeListener(layoutListener);
        }
    }
}