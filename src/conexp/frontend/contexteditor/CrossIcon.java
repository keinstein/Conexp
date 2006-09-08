/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.contexteditor;

import javax.swing.Icon;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

class CrossIcon implements Icon {
    private static final int HEIGHT = 16;
    private static final int WIDTH = 16;

    private static Icon cross;


    public int getIconHeight() {
        return HEIGHT;
    }

    public int getIconWidth() {
        return WIDTH;
    }

    public void paintIcon(Component c, Graphics g, int x, int y) {
        if (c.isEnabled()) {
            g.setColor(Color.black);
        } else {
            g.setColor(Color.gray);
        }
        g.drawLine(x, y + 1, x + WIDTH - 1, y + HEIGHT);
        g.drawLine(x, y, x + WIDTH, y + HEIGHT);
        g.drawLine(x + 1, y, x + WIDTH, y + HEIGHT - 1);
        g.drawLine(x, y + HEIGHT - 1, x + WIDTH - 1, y);
        g.drawLine(x, y + HEIGHT, x + WIDTH, y);
        g.drawLine(x + 1, y + HEIGHT, x + WIDTH, y + 1);
    }


    public static Icon getCross() {
        if (null == cross) {
            cross = new CrossIcon();
        }
        return cross;
    }
}
