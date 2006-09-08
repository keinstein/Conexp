/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package canvas.figures;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;

public class FigureUtils {
    private FigureUtils() {
    }

    public static void calcEllipseBorder(double radius, double centerX, double centerY, Point2D outPoint, Point2D result) {
        double dx = outPoint.getX() - centerX;
        double dy = outPoint.getY() - centerY;
        double len = Math.sqrt(dx * dx + dy * dy);
        dx = radius * dx / len;
        dy = radius * dy / len;
        result.setLocation(centerX + dx, centerY + dy);
    }

    /**
     * Tests if a point is on a line.
     */
    static public boolean pointNearLine(int x1, int y1,
                                        int x2, int y2,
                                        int px, int py, int acceptableDeviation) {

        Rectangle r = new Rectangle(new Point(x1, y1));
        r.add(x2, y2);
        r.grow(acceptableDeviation / 2, acceptableDeviation / 2);
        if (!r.contains(px, py)) {
            return false;
        }

        double a, b, x, y;

        if (x1 == x2) {
            return Math.abs(px - x1) <= acceptableDeviation / 2;
        }
        if (y1 == y2) {
            return Math.abs(py - y1) <= acceptableDeviation / 2;
        }

        a = (double) (y1 - y2) / (double) (x1 - x2);
        b = (double) y1 - a * (double) x1;
        x = (py - b) / a;
        y = a * px + b;

        return Math.min(Math.abs(x - px), Math.abs(y - py)) < acceptableDeviation;
    }
}
