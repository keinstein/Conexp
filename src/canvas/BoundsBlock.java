/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package canvas;

import util.gui.GraphicObjectsFactory;

import java.awt.geom.Rectangle2D;


public class BoundsBlock implements FigureBlock {
    private final Rectangle2D bounds;

    private Rectangle2D temp = GraphicObjectsFactory.makeRectangle2D();

    private boolean first = true;

    public BoundsBlock(Rectangle2D bounds) {
        this.bounds = bounds;
    }

    public void exec(Figure f) {
        f.boundingBox(temp);
        if (first) {
            bounds.setFrame(temp);
            first = false;
        } else {
            bounds.add(temp);
        }
    }

}
