package conexp.frontend.ui;

import javax.swing.*;

/** A Menu_site is a frame that holds a menu bar. Other objects in
 *	the system (which do not have to be visual objects) can negotiate
 *	with the Menu_site to have menu's placed on the site's menu
 *  bar (or withing submenus already found on the menu bar).
 */

// Possible extensions:
// * Stack existing menus and menu items if you add one with the same
//	 name. Restore old item from the stack when menu reverts to previous
//	 state.

public interface MenuSite {
    void addMenu(Object requester, JMenu menu);

    void removeMyMenus(Object requester);



    void addHelpMenu(Object requester, JMenu menu);


    void addMenuItem(Object requester, JMenuItem item,
                       String menuId);
}
