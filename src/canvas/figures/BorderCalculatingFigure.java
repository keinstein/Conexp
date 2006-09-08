/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package canvas.figures;

import java.awt.geom.Point2D;

public interface BorderCalculatingFigure extends IFigureWithCoords {
    void borderAt(Point2D outPoint, Point2D result);
}
