package conexp.frontend.contexteditor;

import javax.swing.*;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

class CrossIcon implements Icon {
    final int height = 16;
    final int width = 16;

    private static Icon cross;


    public int getIconHeight() {
        return height;
    }

    public int getIconWidth() {
        return width;
    }

    public void paintIcon(Component c, Graphics g, int x, int y) {
        if (c.isEnabled()) {
            g.setColor(Color.black);
        } else {
            g.setColor(Color.gray);
        }
        g.drawLine(x, y + 1, x + width - 1, y + height);
        g.drawLine(x, y, x + width, y + height);
        g.drawLine(x + 1, y, x + width, y + height - 1);
        g.drawLine(x, y + height - 1, x + width - 1, y);
        g.drawLine(x, y + height, x + width, y);
        g.drawLine(x + 1, y + height, x + width, y + 1);
    }

    /**
     * Insert the method's description here.
     * Creation date: (22.04.01 21:39:16)
     * @return javax.swing.Icon
     */
    public static Icon getCross() {
        if (null == cross) {
            cross = new CrossIcon();
        }
        return cross;
    }
}