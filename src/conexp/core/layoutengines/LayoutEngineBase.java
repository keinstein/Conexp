/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.layoutengines;

import com.visibleworkings.trace.Trace;
import conexp.core.Lattice;
import conexp.core.layout.ConceptCoordinateMapper;
import conexp.core.layout.LayoutParameters;
import conexp.core.layout.Layouter;
import conexp.core.layout.LayouterProvider;
import util.Assert;

import javax.swing.event.EventListenerList;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public abstract class LayoutEngineBase implements LayoutEngine {

    private LayouterProvider layouterProvider;

    public void init(LayouterProvider provider) {
        this.layouterProvider = provider;
    }

    protected Layouter getLayoter() {
        return layouterProvider.getLayouter();
    }

    private Lattice currLattice;
    private LayoutParameters currParameters;

    protected PropertyChangeListener layoutChangeListener = new LayoutChangeListener();

    class LayoutChangeListener implements PropertyChangeListener {
        public void propertyChange(PropertyChangeEvent evt) {
            Trace.gui.debugm("Layout change ", evt.getPropertyName());
            if (Layouter.LAYOUT_CHANGE.equals(evt.getPropertyName())) {
                ConceptCoordinateMapper mapper = getLayoter();

                fireLayoutChanged(mapper);
            }
            if (Layouter.LAYOUT_PARAMS_CHANGE.equals(evt.getPropertyName())) {
                restartLayout();
            }
        }
    }

    private void restartLayout() {
        Trace.gui.debugm("called restart layout");
        if (null != currLattice && null != currParameters) {
            doRestartLayout(currLattice, currParameters);
        }
    }


    private EventListenerList listenerList = new EventListenerList();

    public void addLayoutListener(LayoutListener listener) {
        listenerList.add(LayoutListener.class, listener);
    }

    public void removeLayoutListener(LayoutListener listener) {
        listenerList.remove(LayoutListener.class, listener);
    }

    private void fireLayoutChanged(ConceptCoordinateMapper mapper) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == LayoutListener.class) {
                ((LayoutListener) listeners[i + 1]).layoutChange(mapper);
            }
        }
    }

    protected abstract void doRestartLayout(Lattice lattice, LayoutParameters parameters);

    public void startLayout(Lattice lattice, LayoutParameters drawParameters) {
        Assert.isTrue(null != lattice);
        Assert.isTrue(null != drawParameters);
        if (lattice.isEmpty()) {
            return;
        }
        currLattice = lattice;
        currParameters = drawParameters;

        doStartLayout(this.currLattice, this.currParameters);

    }

    protected abstract void doStartLayout(Lattice lattice, LayoutParameters parameters);
}
