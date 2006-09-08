/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.ui;

import util.Assert;

import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Vector;


public class DefaultMenuSite
        implements MenuSite, Serializable {
    private JFrame menuFrame;

    private JMenuBar menuBar = null;
    private Map requesters = new HashMap();

    //------------------------------------------------------------
    public DefaultMenuSite(JFrame menuFrame) {
        this.menuFrame = menuFrame;
    }

    //------------------------------------------------------------
    public void addMenu(Object requester, JMenu menu) {
        doAddMenu(requester, menu, false);
    }

    public void addHelpMenu(Object requester, JMenu menu) {
        doAddMenu(requester, menu, true);
    }

    protected JMenuBar getMenuBar() {
        if (menuBar == null) {
            menuBar = new JMenuBar();
            menuFrame.setJMenuBar(menuBar);
        }
        return menuBar;
    }

    private void doAddMenu(Object requester, JMenu menu, final boolean helpMenu) {
        Item item = new Item(menu, getMenuBar(), helpMenu);
        getRequesterMenus(requester).addElement(item);
        item.attachMenuToContainer();
    }

    //------------------------------------------------------------
    public void addMenuItem(Object requester,
                            JMenuItem lineItem, String toThisMenu) {
        Assert.isTrue(requester != null, "null requester");
        Assert.isTrue(lineItem != null, "null menuItem");
        Assert.isTrue(toThisMenu != null, "null menuName");

        JMenu found = findMenuBarSubMenu(toThisMenu);

        Item item = new Item(lineItem, found, false);

        getRequesterMenus(requester).addElement(item);
        item.attachMenuToContainer();
    }

    private JMenu findMenuBarSubMenuByName(String menuName) {
        int bound = menuBar.getMenuCount();
        for (int i = 0; i < bound; i++) {
            final JMenu currMenu = menuBar.getMenu(i);
            if (menuName.equals(currMenu.getName())) {
                return currMenu;
            }
        }
        return null;
    }

    private JMenu findMenuBarSubMenuByLabel(String menuLabel) {
        int bound = menuBar.getMenuCount();
        for (int i = 0; i < bound; i++) {
            final JMenu currMenu = menuBar.getMenu(i);
            if (menuLabel.equals(currMenu.getText())) {
                return currMenu;
            }
        }
        return null;
    }

    private JMenu findMenuBarSubMenu(String toThisMenu) {
        JMenu found = findMenuBarSubMenuByName(toThisMenu);
        if (null == found) {
            found = findMenuBarSubMenuByLabel(toThisMenu);
        }
        if (null == found) {
            throw new IllegalArgumentException("Can't find menu ("
                    + toThisMenu + ")");
        }
        return found;
    }


    /**
     * *********************************************************
     * Return a vector of menu items associated with a given
     * requester. A new (empty) vector is created and returned
     * if there are no menus associated with the requester at
     * present.
     */
    private Vector getRequesterMenus(Object requester) {
        if (requester == null) {
            throw new IllegalArgumentException("Bad argument for getRequsters Menu");
        }
        Assert.isTrue(requesters != null, "No requesters");

        Vector menus = (Vector) (requesters.get(requester));
        if (menus == null) {
            menus = new Vector();
            requesters.put(requester, menus);
        }
        return menus;
    }

    private JMenuBar regenerateMenuBar() {
        // Create the new menu bar and populate it from
        // the current-contents list.

        menuBar = new JMenuBar();
        Iterator i = menuBarContents.iterator();
        while (i.hasNext()) {
            final Item item = ((Item) (i.next()));
            item.addToNewContainer(menuBar);
        }

        // Replace the old menu bar with the new one.
        // Calling setVisible causes the menu bar to be
        // redrawn with a minimum amount of flicker. Without
        // it, the redraw doesn't happen at all.

        menuFrame.setJMenuBar(menuBar);
        menuFrame.setVisible(true);

        return menuBar;
    }

    //------------------------------------------------------------
    public void removeMyMenus(Object requester) {
        Vector menus = (Vector) (requesters.remove(requester));

        if (menus == null) {
            return;
        }
        Iterator iter = menus.iterator();
        while (iter.hasNext()) {
            ((Item) (iter.next()))
                    .detachMenuFromContainer();
        }
    }

    private final LinkedList menuBarContents = new LinkedList();


    private final class Item implements Serializable {
        private final JMenuItem lineItem;
        private JComponent container;
        private final boolean isHelpMenu;

        public Item(JMenuItem lineItem, JComponent container,
                    boolean isHelpMenu) {
            this.lineItem = lineItem;
            this.isHelpMenu = isHelpMenu;
            setContainer(container);
        }

        /**
         * *****************************************************
         * Attach a menu item to it's container (either a menu
         * bar or a menu.) Items are added at the end of the
         * <code>menu_bar_contents</code> list unless a help
         * menu exists, in which case items are added at
         * the penultimate position.
         */

        public final void attachMenuToContainer() {
            if (container instanceof JMenu) {
                doAddLineItemToContainer();
            } else {
                Assert.isTrue(container instanceof JMenuBar);
                if (menuBarContents.isEmpty()) {
                    menuBarContents.add(this);
                    doAddLineItemToContainer();
                } else {
                    Item last = (Item) (menuBarContents.getLast());
                    if (!last.isHelpMenu) {
                        menuBarContents.add(this);
                        doAddLineItemToContainer();
                    } else {
                        // remove the help menu, add the new
                        // item, then put the help menu back
                        // (following the new item).

                        menuBarContents.removeLast();
                        menuBarContents.add(this);
                        menuBarContents.add(last);
                        container = regenerateMenuBar();
                    }
                }
            }
        }

        private void doAddLineItemToContainer() {
            if (container instanceof JMenuBar &&
                    isHelpMenu) {
                container.add(Box.createHorizontalGlue());
            }
            container.add(lineItem);
        }

        private void addToNewContainer(JComponent container) {
            setContainer(container);
            doAddLineItemToContainer();
        }

        private void setContainer(JComponent container) {
            Assert.isTrue(container instanceof JMenu ||
                    container instanceof JMenuBar);
            Assert.isTrue(!isHelpMenu || container instanceof JMenuBar, "For help menu container should be a JMenuBar");
            this.container = container;

        }

        /**
         * *****************************************************
         * Remove the current menu item from its container
         * (either a menu bar or a menu.)
         */
        public final void detachMenuFromContainer() {
            container.remove(lineItem);
            if (container instanceof JMenuBar) {
                menuBarContents.remove(this);
                container = regenerateMenuBar();
            }
        }
    }


}


