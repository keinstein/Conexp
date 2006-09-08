/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.util;

import conexp.frontend.ResourceLoader;
import conexp.util.gui.ToggleAbstractAction;

import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import java.awt.Dimension;
import java.awt.Insets;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ResourceBundle;


public class ToolBuilder {


    private ActionMap actChain;
    private final static Insets insets0 = new Insets(1, 1, 1, 1);


    //----------------------------------------------
    // Yarked from JMenu, ideally this would be public.
    private static class ActionChangedListener implements PropertyChangeListener {
        JMenuItem menuItem;

        ActionChangedListener(JMenuItem mi) {
            super();
            this.menuItem = mi;
        }

        public void propertyChange(PropertyChangeEvent e) {
            String propertyName = e.getPropertyName();
            if (e.getPropertyName().equals(Action.NAME)) {
                String text = (String) e.getNewValue();
                menuItem.setText(text);
            } else if (propertyName.equals("enabled")) {
                Boolean enabledState = (Boolean) e.getNewValue();
                menuItem.setEnabled(enabledState.booleanValue());
            }
        }
    }

    private IResourceManager resManager;

    /**
     * @deprecated
     */
    public ToolBuilder(ResourceBundle res, ActionMap actChain) {
        this(new ResourceManager(res), actChain);
    }

    public ToolBuilder(IResourceManager resourceManager, ActionMap actChain) {
        this.resManager = resourceManager;
        this.actChain = actChain;
    }

    //----------------------------------------------
    private static PropertyChangeListener createActionChangeListener(JMenuItem b) {
        return new ActionChangedListener(b);
    }

    //----------------------------------------------
    private static AbstractButton createButton(boolean isToggle) {
        return (isToggle ? (AbstractButton) new JToggleButton() : (AbstractButton) new JButton());
    }
    //----------------------------------------------

    /**
     * Create a menu for the app.  By default this pulls the
     * definition of the menu from the associated resource file.
     */
    private JMenu createMenu(String key) {
        String[] itemKeys = resManager.getResourceDescription(key);
        JMenu menu = makeMenu(key, resManager.getCommandLabel(key));
        for (int i = 0; i < itemKeys.length; i++) {
            addToMenu(menu, itemKeys[i]);
        }
        return menu;
    }
    //----------------------------------------------

    /**
     * Create the menubar for the app.  By default this pulls the
     * definition of the menu from the associated resource file.
     */
    public JMenuBar createMenuBar() {
        JMenuBar mb = new JMenuBar();
        String[] menuKeys = resManager.getResourceDescription("menubar");
        for (int i = 0; i < menuKeys.length; i++) {
            addToMenuBar(mb, menuKeys[i]);
        }
        return mb;
    }

    public JMenu createMenu() {
        String[] menu = resManager.getResourceDescription("menu");
        return createMenu(menu[0]);
    }

    public JMenu createHelpMenu() {
        String[] menu = resManager.getResourceDescription("helpMenu");
        return createMenu(menu[0]);
    }


    //----------------------------------------------
    protected JMenuItem createMenuItem(String name, String label, Icon icon, String shortCut, Action a) {
        if (null == label) {
            return null;
        } // end of if ()

        JMenuItem mi = makeMenuItem(name, label);
        if (null != icon) {
            mi.setIcon(icon);
        }
        if (null != shortCut) {
            KeyStroke acc = KeyStroke.getKeyStroke(shortCut);
            mi.setAccelerator(acc);
        }

        if (a != null) {
            mi.addActionListener(a);
            a.addPropertyChangeListener(createActionChangeListener(mi));
            mi.setEnabled(a.isEnabled());
        } else {
            mi.setEnabled(false);
        }
        return mi;
    }

    //----------------------------------------------
    public JToolBar createToolBar(int orientation) {
        JToolBar ret = new JToolBar(orientation);
        String[] toolKeys = resManager.getResourceDescription("toolbar");
        for (int i = 0; i < toolKeys.length; i++) {
            addToToolbar(ret, toolKeys[i]);
        }
        return ret;
    }

    //----------------------------------------------
    protected AbstractButton createToolbarButton(String command, Icon icon, boolean isToggle, String tooltip, Action action) {
        final AbstractButton but = createButton(isToggle);
        but.setIcon(icon);
        if (isToggle) {
            but.setSelectedIcon(ResourceLoader.getIcon(resManager.getSelectedImage(command)));
        }
        // but.setPreferredSize(new Dimension(27, 27));
        but.setToolTipText(tooltip);
        but.setContentAreaFilled(false);
        but.setMargin(insets0);
        but.setRequestFocusEnabled(false);
        if (null != action) {
            but.addActionListener(action);
            action.addPropertyChangeListener(new PropertyChangeListener() {
                public void propertyChange(PropertyChangeEvent evt) {
                    if (evt.getPropertyName().equals("enabled")) {
                        but.setEnabled(((Boolean) evt.getNewValue()).booleanValue());
                    }
                }
            });
            but.setEnabled(action.isEnabled());
        }
        if (isToggle && action instanceof ToggleAbstractAction) {
            but.setSelected(((ToggleAbstractAction) action).isSelected());
        }
        return but;
    }


    /**
     * Insert the method's description here.
     * Creation date: (23.05.01 20:12:20)
     */
    private void addToMenu(JMenu menu, String command) {
        if (command.equals("-")) {
            menu.addSeparator();
        } else if (command.startsWith("+")) {
            command = command.substring(1);
            menu.add(makeMenu(command, resManager.getCommandLabel(command)));
        } else {
            JMenuItem mi = createMenuItem(command, resManager.getCommandLabel(command),
                    ResourceLoader.getIcon(resManager.getCommandImage(command)),
                    resManager.getCommandShortcut(command),
                    actChain.get(command));
            menu.add(mi);
        }
    }


    /**
     * Insert the method's description here.
     * Creation date: (23.05.01 21:16:42)
     */
    private void addToMenuBar(JMenuBar mb, String command) {
        JMenu m = createMenu(command);
        if (m != null) {
            mb.add(m);
        }
    }


    /**
     * Insert the method's description here.
     * Creation date: (23.05.01 20:03:25)
     */
    private void addToToolbar(JToolBar tool, String command) {
        if (command.equals("-")) {
            tool.addSeparator(new Dimension(3, 3));
        } else {
            boolean isToggle = false;
            if (command.startsWith("*")) {
                isToggle = true;
                command = command.substring(1);

            }

            AbstractButton button = createToolbarButton(command, ResourceLoader.getIcon(resManager.getCommandImage(command)),
                    isToggle,
                    resManager.getCommandTooltip(command),
                    actChain.get(command));
            tool.add(button);
        }
    }


    /**
     * Insert the method's description here.
     * Creation date: (23.05.01 20:13:54)
     */
    private static JMenu makeMenu(String name, String label) {
        int mnemonic = 0;
        if (null != label) {
            int pos = label.indexOf('&');
            if (-1 != pos) {
                mnemonic = label.charAt(pos + 1);
                label = label.substring(0, pos) + label.substring(pos + 1);
            }
        } // end of if ()
        JMenu menu = new JMenu(label);
        menu.setName(name);
        if (0 != mnemonic) {
            menu.setMnemonic(mnemonic);
        }
        return menu;
    }


    /**
     * Insert the method's description here.
     * Creation date: (23.05.01 20:13:54)
     */
    private static JMenuItem makeMenuItem(String name, String label) {
        int mnemonic = 0;
        if (null != label) {
            int pos = label.indexOf('&');
            if (-1 != pos) {
                mnemonic = label.charAt(pos + 1);
                label = label.substring(0, pos) + label.substring(pos + 1);
            }
        } // end of if ()
        JMenuItem menu = new JMenuItem(label);
        menu.setName(name);
        if (0 != mnemonic) {
            menu.setMnemonic(mnemonic);
        }
        return menu;
    }
}
