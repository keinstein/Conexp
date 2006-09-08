/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.core.layout;



import util.gui.GraphicObjectsFactory;

import java.awt.geom.Point2D;

public class PointUtilities {
    private PointUtilities() {
    }

    public static double length(Point2D point2D) {
        return point2D.distance(0, 0);
    }

    public static double dotProduct(Point2D first, Point2D second) {
        return first.getX() * second.getX() + first.getY() * second.getY();
    }

    public static double correlation(Point2D first, Point2D second) {
        double len1 = length(first);
        double len2 = length(second);
        if (len1 == 0.0 || len2 == 0.0) {
            return 0.0;
        } else {
            return dotProduct(first, second) / (len1 * len2);
        }
    }

    public static Point2D normalizedEdgeVector(final Point2D nodeCoords, final Point2D childCoords) {
        double distance = nodeCoords.distance(childCoords);
        double dx = childCoords.getX() - nodeCoords.getX();
        double dy = childCoords.getY() - nodeCoords.getY();
        Point2D vector = GraphicObjectsFactory.makePoint2D(dx / distance,
                dy / distance);
        return vector;
    }
}
