/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package canvas;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public interface Figure {
    void removeFigureListener();

    void setFigureListener(FigureListener newFListener);

    void setCoords(double x, double y);

    void moveBy(double dx, double dy);

    void boundingBox(Rectangle2D rect);

    void draw(Graphics g, CanvasScheme opt);

    boolean contains(double x, double y);

    Figure findFigureInside(double x, double y);

    Figure findFigureInsideExceptFor(double x, double y, Figure toExclude);


    void visit(BaseFigureVisitor visitor);
}
