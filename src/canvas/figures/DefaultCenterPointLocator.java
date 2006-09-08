/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package canvas.figures;

public class DefaultCenterPointLocator implements CenterPointLocator {
    double x;
    double y;

    public double getCenterX() {
        return x;
    }

    public double getCenterY() {
        return y;
    }

    public void setCenterCoords(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return "DefaultCenterPointLocator{" +
                "x=" + x +
                ", y=" + y +
                "}";
    }
}
