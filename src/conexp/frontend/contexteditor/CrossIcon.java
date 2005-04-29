/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.contexteditor;

import javax.swing.*;
import java.awt.*;

class CrossIcon implements Icon {
    private static final int height = 16;
    private static final int width = 16;

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


    public static Icon getCross() {
        if (null == cross) {
            cross = new CrossIcon();
        }
        return cross;
    }
}
