/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.util.gui;

import javax.swing.AbstractAction;
import javax.swing.Icon;



public abstract class ToggleAbstractAction extends AbstractAction {
    public ToggleAbstractAction() {
    }

    public ToggleAbstractAction(String name) {
        super(name);
    }

    public ToggleAbstractAction(String name, Icon icon) {
        super(name, icon);
    }

    public abstract boolean isSelected();
}
