/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/

package conexp.frontend;

import conexp.frontend.ui.ViewManager;

import javax.swing.*;
import javax.swing.event.ChangeListener;


public class JTabPaneViewManager extends ViewManager {
    protected javax.swing.JTabbedPane tabPane;

    /**
     * JTabPaneViewManager constructor comment.
     */
    public JTabPaneViewManager() {
        super();
    }


    /**
     * Insert the method's description here.
     * Creation date: (14.05.2001 16:56:52)
     * @return javax.swing.JTabbedPane
     */
    protected JTabbedPane getTabPane() {
        if (null == tabPane) {
            tabPane = new JTabbedPane(JTabbedPane.BOTTOM);
            tabPane.addChangeListener(
                    new ChangeListener() {
                        public void stateChanged(javax.swing.event.ChangeEvent e) {
                            JComponent newView = (JComponent) getTabPane().getSelectedComponent();
                            setActiveView(newView);
                        }
                    }
            );
        }
        return tabPane;
    }


    /**
     * Insert the method's description here.
     * Creation date: (14.05.2001 16:58:07)
     * @return java.awt.Container
     */
    public java.awt.Container getViewContainer() {
        return getTabPane();
    }


    /**
     * Insert the method's description here.
     * Creation date: (14.05.2001 15:21:51)
     * @param view javax.swing.JComponent
     */
    protected void doActivate(JComponent view) {
        getTabPane().setSelectedComponent(view);
    }


    /**
     * Insert the method's description here.
     * Creation date: (14.05.2001 17:33:12)
     * @param view javax.swing.JComponent
     */
    public void doAddView(View view, String caption) {
        getTabPane().addTab(caption, (JComponent) view);
    }


    /**
     * Insert the method's description here.
     * Creation date: (14.05.2001 15:25:40)
     */
    protected void doRemoveView(JComponent view) {
        getTabPane().remove(view);
    }
}
