/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package canvas.figures;

import canvas.Figure;

import java.awt.geom.Point2D;

public interface BorderCalculatingFigure extends Figure {
    void borderAt(Point2D outPoint, Point2D result);

    Point2D getCenter();
}
