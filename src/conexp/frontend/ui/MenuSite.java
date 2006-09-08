/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.ui;

import javax.swing.JMenu;
import javax.swing.JMenuItem;


public interface MenuSite {
    void addMenu(Object requester, JMenu menu);

    void removeMyMenus(Object requester);


    void addHelpMenu(Object requester, JMenu menu);


    void addMenuItem(Object requester, JMenuItem item,
                     String menuId);
}
