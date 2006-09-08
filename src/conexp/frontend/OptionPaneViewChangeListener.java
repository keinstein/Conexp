/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend;

import javax.swing.JComponent;


public class OptionPaneViewChangeListener implements ViewChangeListener {
    private javax.swing.JComponent optPane;

    /**
     * OptionPaneViewChangeListener constructor comment.
     */
    public OptionPaneViewChangeListener(javax.swing.JComponent optPane) {
        super();
        this.optPane = optPane;
    }

    /**
     * viewChanged method comment.
     */
    public void viewChanged(JComponent oldView, JComponent newView) {
        if (null != optPane) {
            if (null != oldView && (oldView instanceof OptionPaneProvider)) {
                optPane.remove(((OptionPaneProvider) oldView).getViewOptions());
            }
            if (null != newView && (newView instanceof OptionPaneProvider)) {
                optPane.add(((OptionPaneProvider) newView).getViewOptions());
            }
            optPane.repaint();
            optPane.revalidate();
        }
    }

    public void cleanUp() {
        optPane.removeAll();
    }
}
