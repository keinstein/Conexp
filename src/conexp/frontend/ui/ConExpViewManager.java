/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.ui;

import conexp.frontend.View;
import conexp.frontend.ViewChangeListener;
import util.collection.CollectionFactory;

import javax.swing.JComponent;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.Container;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ConExpViewManager {

    static class NullView extends JComponent implements View {
        public NullView() {
        }

        public void initialUpdate() {
            repaint();
        }
    }

    private NullView nullComponent = new NullView() {
    };

    private Map viewMap = CollectionFactory.createDefaultMap();

    public boolean hasViewForModel(IViewInfo docModel) {
        return viewMap.containsKey(docModel);
    }


    public View getView(IViewInfo viewInfo) {
        if (hasViewForModel(viewInfo)) {
            return doGetView(viewInfo);
        } else {
            View view = viewInfo.createView();
            view.initialUpdate();
            viewMap.put(viewInfo, view);
            return view;
        }
    }


    public void removeView(IViewInfo viewInfo) {
        if (!hasViewForModel(viewInfo)) {
            return;
        }
        View view = getView(viewInfo);
        if (viewIsShown(view)) {
            removeViewFromPlace(view);
        }
        viewMap.remove(viewInfo);
        //here order is important
        if (hasNoViewsWithSameCaption(viewInfo)) {
            int index = getTabPane().indexOfTab(viewInfo.getViewCaption());
            getTabPane().removeTabAt(index);
        }
    }

    public int getPlacesCount() {
        return getTabPane().getTabCount();
    }

    private boolean hasNoViewsWithSameCaption(IViewInfo viewInfo) {
        Set set = viewMap.keySet();
        for (Iterator iterator = set.iterator(); iterator.hasNext();) {
            IViewInfo other = (IViewInfo) iterator.next();
            if (viewInfo.getViewCaption().equals(other.getViewCaption())) {
                return false;
            }
        }
        return true;
    }

    private void removeViewFromPlace(View view) {
        int viewPosition = findViewPosition(view);
        //assert viewPosition!=-1;
        getTabPane().setComponentAt(viewPosition, nullComponent);
        if (view == getActiveView()) {
            setActiveView(null);
        }
    }

    private boolean viewIsShown(View view) {
        return findViewPosition(view) != -1;
    }

    private int findViewPosition(View view) {
        return getTabPane().indexOfComponent((JComponent) view);
    }

    public void activateView(IViewInfo viewInfo) {
        addView(viewInfo);
        activatePlace(viewInfo);
    }

    public void addView(IViewInfo viewInfo) {
        View view = getView(viewInfo);
        setActiveViewForPlace(viewInfo, view);
    }

    private JTabbedPane tabPane;

    protected JTabbedPane getTabPane() {
        if (null == tabPane) {
            tabPane = new JTabbedPane(JTabbedPane.BOTTOM);
            tabPane.addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent e) {
                    JComponent newView = (JComponent) getTabPane().getSelectedComponent();
                    if (newView != null) {
                        setActiveView((View) newView);
                    }
                }
            });
        }
        return tabPane;
    }

    private void activatePlace(IViewInfo viewPlace) {
        int index = getViewPlaceIndex(viewPlace);
        //assert -1!=index;
        getTabPane().setSelectedIndex(index);
    }

    private int getViewPlaceIndex(IViewInfo viewInfo) {
        return getTabPane().indexOfTab(viewInfo.getViewCaption());
    }

    private void setActiveViewForPlace(IViewInfo viewInfo, View view) {
        int index = getViewPlaceIndex(viewInfo);
        if (-1 == index) {
            getTabPane().addTab(viewInfo.getViewCaption(), (JComponent) view);
            index = getTabPane().getComponentCount() - 1;
        } else {
            getTabPane().setComponentAt(index, (JComponent) view);
        }
        getTabPane().setSelectedIndex(index);

//        getTabPane().revalidate();

        setActiveView(view);
    }

    private View activeView;

    private void setActiveView(View view) {
        if (activeView != view) {
            View oldView = activeView;
            activeView = view;
            if (null != activeView) {
                ((JComponent) activeView).repaint();
            }
            fireViewChanged((JComponent) oldView, (JComponent) activeView);
        }
    }

    public View getActiveView() {
        return activeView;
    }

    public Container getViewContainer() {
        return getTabPane();
    }

    protected List viewChangeListeners;

    protected java.util.List getViewChangeListeners() {
        if (null == viewChangeListeners) {
            viewChangeListeners = new ArrayList();
        }
        return viewChangeListeners;
    }

    public void addViewChangeListener(ViewChangeListener listener) {
        getViewChangeListeners().add(listener);
    }

    public void removeViewChangeListener(ViewChangeListener listener) {
        getViewChangeListeners().remove(listener);
    }

    protected void fireViewChanged(JComponent oldView, JComponent newView) {
        Iterator iter = getViewChangeListeners().iterator();
        while (iter.hasNext()) {
            ((ViewChangeListener) iter.next()).viewChanged(oldView, newView);
        }
    }

    //TEST INTERFACE
    public Collection getViews() {
        return Collections.unmodifiableCollection(viewMap.values());
    }

    public String getActiveViewId() {
        //actually return active view place
        final IViewInfo viewInfo = getViewInfo(activeView);
        if (viewInfo != null) {
            return viewInfo.getViewPlace();
        }
        return "";
    }

    public View doGetView(IViewInfo viewInfo) {
        return (View) viewMap.get(viewInfo);
    }

    public IViewInfo getViewInfo(View view) {
        for (Iterator iterator = viewMap.entrySet().iterator();
             iterator.hasNext();) {
            Map.Entry entry = (Map.Entry) iterator.next();
            if (entry.getValue() == view) {
                return ((IViewInfo) entry.getKey());
            }
        }
        return null;
    }

}
