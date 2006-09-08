/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package canvas.figures;

import canvas.CanvasScheme;
import util.gui.GraphicObjectsFactory;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;


public class TrackerRectangularFigure extends RectangularFigure {

    public TrackerRectangularFigure(double x, double y, double w, double h) {
        super(x, y, w, h);
    }

    public TrackerRectangularFigure(Rectangle2D rect) {
        super(rect);
    }

    public TrackerRectangularFigure() {
    }

    public void draw(Graphics g, CanvasScheme opt) {
        Graphics2D g2D = (Graphics2D) g;

        Rectangle2D rect = GraphicObjectsFactory.makeRectangle2D();
        boundingBox(rect);

        g2D.setColor(opt.getColorScheme().getNodeBorderColor());
        g2D.draw(rect);
    }

    public void setRectangle(Rectangle2D rect) {
        setWidth(rect.getWidth());
        setHeight(rect.getHeight());
        setCoords(rect.getCenterX(), rect.getCenterY());
    }
}
