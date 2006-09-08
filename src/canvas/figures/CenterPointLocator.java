/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package canvas.figures;

public interface CenterPointLocator {
    double getCenterX();

    double getCenterY();

    void setCenterCoords(double x, double y);
}
