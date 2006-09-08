/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend;


public interface ViewFactory {

    /**
     * Insert the method's description here.
     * Creation date: (14.05.2001 14:06:53)
     *
     * @param viewType java.lang.String
     * @return javax.swing.JComponent
     */
    View makeView(String viewType);
}
