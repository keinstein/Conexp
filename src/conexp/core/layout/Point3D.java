package conexp.core.layout;

/* Point3D.java	(c) Ralph Freese 97/01/07

 * I am adding code which saves the last force calculation and takes the
 * dot product with the new force. If this is positive the movement is
 * increased but if it is negative it is decreased. This should prevent
 * wild oscillations. The formula I am using is to multiply v_new
 * (the new force) by
 *
 *                v_new \cdot v_old
 *        1 + 0.5 -----------------
 *                |v_new| * |v_old|
 *
 * 0.5 is the constant named factor; there is nothing scientific about it;
 * it just seems to work well.
 */


public class Point3D extends Point2D {
    public int z;
    private Point2D currentForce;
    private Point2D previousForce;
    private static float factor = (float) 0.5;
    Point2D proj2d;
    float normalizedX;	// These will be x, y, z scaled so that they are
    float normalizedY;	// between 0 and 1.
    float normalizedZ;

    public Point3D() {
        currentForce = new Point2D();
        previousForce = null;
        proj2d = new Point2D();
    }

    public Point2D getProjection() {
        return proj2d;
    }

    public float getX() {
        return proj2d.x;
    }

    public float getY() {
        return proj2d.y;
    }

    public void update() {
        float correction = (float) 1.0;
        if (previousForce != null) {
            correction = (float) 1.0
                    + factor * currentForce.correlation(previousForce);
        } else {
            previousForce = new Point2D();
        }
        x += correction * currentForce.x;
        y += correction * currentForce.y;
        previousForce.x = currentForce.x;
        previousForce.y = currentForce.y;
        currentForce.x = (float) 0.0;
        currentForce.y = (float) 0.0;
    }

    public void adjustForce(float dx, float dy) {
        currentForce.x += dx;
        currentForce.y += dy;
    }

    protected void setNormalizedCoords(float scaleFactor) {
        normalizedX = x / scaleFactor;
        normalizedY = y / scaleFactor;
        normalizedZ = z / scaleFactor;
    }


    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")"
                + "[" + proj2d.x + ", " + proj2d.y + "]";
    }

    private Point3D deltaMove;

    /**
     * Insert the method's description here.
     * Creation date: (04.03.01 10:40:19)
     * @return double
     * @param one conexp.frontend.latticeeditor.Layout.Point3D
     * @param two conexp.frontend.latticeeditor.Layout.Point3D
     */
    public static double distance(Point3D one, Point3D two) {
        double dx = one.x - two.x;
        double dy = one.y - two.y;
        double dz = one.z - two.z;
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    /**
     * Insert the method's description here.
     * Creation date: (04.03.01 11:20:07)
     * @return double
     */
    public double size() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    public void project2d(double angle) {
        proj2d.x = (float) Math.cos(angle) * normalizedX
                + (float) Math.sin(angle) * normalizedY;
        proj2d.y = normalizedZ;
    }
}