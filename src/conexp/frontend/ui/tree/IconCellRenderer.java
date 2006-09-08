/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.frontend.ui.tree;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;


public class IconCellRenderer extends JLabel implements TreeCellRenderer {
    protected Color textSelectionColor;
    protected Color textNonSelectionColor;
    protected Color backSelectionColor;
    protected Color backNonSelectionColor;
    protected Color borderSelectionColor;

    protected boolean selected;

    public IconCellRenderer() {
        super();
        textSelectionColor = UIManager.getColor("Tree.selectionForeground");
        textNonSelectionColor = UIManager.getColor("Tree.textForeground");
        backSelectionColor = UIManager.getColor("Tree.selectionBackground");
        backNonSelectionColor = UIManager.getColor("Tree.textBackground");
        borderSelectionColor = UIManager.getColor("Tree.selectionBorderColor");
        setOpaque(false);
    }

    public Component getTreeCellRendererComponent(JTree tree,
                                                  Object value, boolean sel, boolean expanded, boolean leaf,
                                                  int row, boolean hasFocus) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
        Object obj = node.getUserObject();
        setText(obj.toString());

        if (obj instanceof IconData) {
            IconData idata = (IconData) obj;
            if (expanded) {
                setIcon(idata.getExpandedIcon());
            } else {
                setIcon(idata.getIcon());
            }
        } else {
            setIcon(null);
        }

        setFont(tree.getFont());
        setForeground(sel ? textSelectionColor :
                textNonSelectionColor);
        setBackground(sel ? backSelectionColor :
                backNonSelectionColor);
        selected = sel;
        return this;
    }

    public void paintComponent(Graphics g) {
        Color bColor = getBackground();
        Icon icon = getIcon();

        g.setColor(bColor);
        int offset = 0;
        if (icon != null && getText() != null) {
            offset = (icon.getIconWidth() + getIconTextGap());
        }
        g.fillRect(offset, 0, getWidth() - 1 - offset,
                getHeight() - 1);

        if (selected) {
            g.setColor(borderSelectionColor);
            g.drawRect(offset, 0, getWidth() - 1 - offset, getHeight() - 1);
        }
        super.paintComponent(g);
    }
}
