/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package canvas;

import java.awt.Graphics;
import java.awt.Graphics2D;


public class PaintBlock implements FigureBlock {
    Graphics2D g;
    private CanvasScheme opt;

    public PaintBlock(CanvasScheme opt) {
        super();
        this.opt = opt;
    }

    public void exec(Figure f) {
        f.draw(g, getOpt());
    }

    public Graphics2D getGraphics2D() {
        return g;
    }

    CanvasScheme getOpt() {
        return opt;
    }

    public void setGraphics(Graphics graphics) {
        g = (Graphics2D) graphics;
    }
}
