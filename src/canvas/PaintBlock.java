/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package canvas;

import java.awt.Graphics;
import java.awt.Graphics2D;


public class PaintBlock implements FigureBlock {
    Graphics2D graphics = null;
    private CanvasScheme opt;

    public PaintBlock(CanvasScheme opt) {
        super();
        this.opt = opt;
    }

    public void exec(Figure f) {
        f.draw(graphics, getOpt());
    }

    public Graphics2D getGraphics2D() {
        return graphics;
    }

    CanvasScheme getOpt() {
        return opt;
    }

    public void setGraphics(Graphics graphics) {
        this.graphics = (Graphics2D) graphics;
    }
}
