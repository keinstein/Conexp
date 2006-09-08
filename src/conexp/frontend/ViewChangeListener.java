/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend;

import javax.swing.JComponent;


public interface ViewChangeListener {
    /**
     * Insert the method's description here.
     * Creation date: (06.12.00 11:35:19)
     *
     * @param oldView conexp.frontend.ViewChangeInterface
     * @param newView conexp.frontend.ViewChangeInterface
     */
    void viewChanged(JComponent oldView, JComponent newView);

    void cleanUp();
}
