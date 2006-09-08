/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.frontend.ui.tree;

import javax.swing.Icon;


public class IconData {
    protected Icon icon;
    protected Icon expandedIcon;
    protected Object data;

    public IconData(Icon icon, Object data) {
        this(icon, null, data);
    }

    public IconData(Icon icon, Icon expandedIcon, Object data) {
        this.icon = icon;
        this.expandedIcon = expandedIcon;
        this.data = data;
    }

    public Icon getIcon() {
        return icon;
    }

    public Icon getExpandedIcon() {
        return expandedIcon != null ? expandedIcon : icon;
    }

    public Object getObject() {
        return data;
    }

    public String toString() {
        return data.toString();
    }
}
