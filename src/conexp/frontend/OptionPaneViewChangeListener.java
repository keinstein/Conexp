package conexp.frontend;

import javax.swing.*;


/**
 * Insert the type's description here.
 * Creation date: (06.12.00 12:27:55)
 * @author
 */
public class OptionPaneViewChangeListener implements ViewChangeListener {
    protected javax.swing.JComponent optPane;

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