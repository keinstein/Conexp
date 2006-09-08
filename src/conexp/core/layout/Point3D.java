/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.layout;

import java.awt.geom.Point2D;

public class Point3D extends Point2D.Double {
    public int z;
    private Point2D currentForce;
    private Point2D previousForce;
    private static float factor = (float) 0.5;
    Point2D proj2d;
    float normalizedX;    // These will be x, y, z scaled so that they are
    float normalizedY;    // between 0 and 1.
    float normalizedZ;

    public Point3D() {
        currentForce = makePoint2D();
        previousForce = null;
        proj2d = makePoint2D();
    }

    private static Point2D.Double makePoint2D() {
        return new Point2D.Double();
    }

    public Point2D getProjection() {
        return proj2d;
    }

    public float getProjectedX() {
        return (float) proj2d.getX();
    }

    public float getProjectedY() {
        return (float) proj2d.getY();
    }

    public void update() {
        double correction = 1.0;
        if (previousForce != null) {
            correction = 1.0
                    + factor * PointUtilities.correlation(currentForce, previousForce);
        } else {
            previousForce = makePoint2D();
        }
        this.setLocation(getX() + correction * currentForce.getX(),
                getY() + correction * currentForce.getY());

        previousForce.setLocation(currentForce);
        currentForce.setLocation(0.0, 0.0);
    }

    public void adjustForce(double dx, double dy) {
        currentForce.setLocation(currentForce.getX() + dx,
                currentForce.getY() + dy);
    }

    protected void setNormalizedCoords(float scaleFactor) {
        normalizedX = (float) getX() / scaleFactor;
        normalizedY = (float) getY() / scaleFactor;
        normalizedZ = z / scaleFactor;
    }


    public String toString() {
        return "(" + getX() + ", " + getY() + ", " + z + ')'
                + '[' + proj2d.getX() + ", " + proj2d.getY() + ']';
    }

    public static double distance(Point3D one, Point3D two) {
        double dx = one.getX() - two.getX();
        double dy = one.getY() - two.getY();
        double dz = one.z - two.z;
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    /**
     * Insert the method's description here.
     * Creation date: (04.03.01 11:20:07)
     *
     * @return double
     */
    public double size() {
        return Math.sqrt(getX() * getX() + getY() * getY() + z * z);
    }

    public void project2d(double angle) {
        proj2d.setLocation(Math.cos(angle) * normalizedX + Math.sin(angle) * normalizedY,
                normalizedZ);
    }
}
