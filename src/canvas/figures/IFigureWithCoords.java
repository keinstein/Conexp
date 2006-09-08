/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package canvas.figures;

import canvas.Figure;

import java.awt.geom.Point2D;

public interface IFigureWithCoords extends Figure {
    double getCenterX();

    double getCenterY();

    Point2D getCenter();

}
