/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor.figures;

import canvas.CanvasScheme;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

public class ConnectorEndFigure extends NodeFigure {
    public ConnectorEndFigure() {
        setRadius(3);
    }


    public void draw(Graphics g, CanvasScheme opt) {
        Graphics2D g2D = (Graphics2D) g;
        g2D.setColor(opt.getColorScheme().getNodeColor());
        Ellipse2D ellipse = getFigureEllipse();
        g2D.fill(ellipse);
    }

    public boolean contains(double x, double y) {
        return false;
    }


}
