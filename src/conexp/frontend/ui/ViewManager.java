package conexp.frontend.ui;

import conexp.frontend.View;
import conexp.frontend.ViewChangeListener;
import conexp.frontend.ViewFactory;

import javax.swing.*;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Insert the type's description here.
 * Creation date: (14.05.2001 14:03:54)
 * @author
 */
public class ViewManager {

    protected ViewMap viewMap = new ViewMap();

    protected ViewFactory viewFactory;
    protected HashMap views;
    protected java.util.ArrayList viewChangeListeners;


    protected String getViewCaption(String name) {
        return viewMap.getViewCaption(name);
    }


    protected String getViewType(String name) {
        return viewMap.getViewType(name);
    }


    public void registerView(String id, String type, String caption) throws ViewManagerException {
       viewMap.registerView(id, type, caption);
    }

    protected javax.swing.JComponent activeView;

    public ViewManager() {
        super();
    }

    public void activateView(String id) throws ViewManagerException {
        if (!getViews().containsKey(id)) {
            addView(id);
        }
        doActivate(getView(id));
    }


    public void addView(String name) throws ViewManagerException {
        String type = getViewType(name);
        if ("".equals(type)) {
            throw new ViewManagerException("View with name " + name + " was'nt registered. Use registerView");
        }
        View view = viewFactory.makeView(type);
        if (null == view) {
            throw new ViewManagerException("Factory can't create view of type " + type);
        }
        getViews().put(name, view);
        view.initialUpdate();
        doAddView(view, getViewCaption(name));
    }

    public void addViewChangeListener(ViewChangeListener listener) {
        getViewChangeListeners().add(listener);
    }


    protected void fireViewChanged(JComponent oldView, JComponent newView) {
        Iterator iter = getViewChangeListeners().iterator();
        while (iter.hasNext()) {
            ((ViewChangeListener) iter.next()).viewChanged(oldView, newView);
        }
    }


    /**
     * Insert the method's description here.
     * Creation date: (14.05.2001 15:22:56)
     * @return javax.swing.JComponent
     * @param name java.lang.String
     */
    public JComponent getView(String name) {
        return (JComponent) getViews().get(name);
    }


    protected java.util.List getViewChangeListeners() {
        if (null == viewChangeListeners) {
            viewChangeListeners = new java.util.ArrayList();
        }
        return viewChangeListeners;
    }


    protected java.util.Map getViews() {
        if (null == views) {
            views = new HashMap();
        }
        return views;
    }

    public void removeView(String name) {
        JComponent view = getView(name);
        if (null != view) {
            doRemoveView(view);
        }
        getViews().remove(name);
    }

    public void removeViewChangeListener(ViewChangeListener listener) {
        getViewChangeListeners().remove(listener);
    }


    public void setViewFactory(ViewFactory newViewFactory) {
        viewFactory = newViewFactory;
    }

    protected void doActivate(JComponent view) {
        setActiveView(view);
    }

    protected void doAddView(View view, String caption) {
    }

    protected void doRemoveView(JComponent view) {
    }

    public javax.swing.JComponent getActiveView() {
        return activeView;
    }
    protected void setActiveView(JComponent view) {
        if (activeView != view) {
            JComponent oldView = activeView;
            activeView = view;
            fireViewChanged(oldView, activeView);
        }
    }
}