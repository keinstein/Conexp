package conexp.frontend;

import javax.swing.*;

/**
 * Insert the type's description here.
 * Creation date: (06.12.00 11:32:05)
 * @author
 */
public interface ViewChangeListener {
    /**
     * Insert the method's description here.
     * Creation date: (06.12.00 11:35:19)
     * @param oldView conexp.frontend.ViewChangeInterface
     * @param newView conexp.frontend.ViewChangeInterface
     */
    void viewChanged(JComponent oldView, JComponent newView);

    void cleanUp();
}