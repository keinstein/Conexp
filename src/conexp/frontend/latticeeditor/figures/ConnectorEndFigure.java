/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor.figures;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class ConnectorEndFigure extends NodeFigure implements BorderCalculatingLineDiagramFigure {
    public ConnectorEndFigure() {
        setRadius(3);
    }


    public void draw(Graphics g, canvas.CanvasScheme opt) {
        Graphics2D g2D = (Graphics2D) g;
        g2D.setColor(opt.getColorScheme().getNodeColor());
        Ellipse2D ellipse = getFigureEllipse();
        g2D.fill(ellipse);
    }

    public boolean contains(double x, double y) {
        return false;
    }


}
