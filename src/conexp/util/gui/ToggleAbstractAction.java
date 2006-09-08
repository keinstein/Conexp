package conexp.util.gui;

import javax.swing.AbstractAction;
import javax.swing.Icon;

/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 * Date: 6/4/2004
 * Time: 15:08:53
 */

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
