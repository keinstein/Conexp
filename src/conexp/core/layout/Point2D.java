package conexp.core.layout;

class Point2D {
    public float x;
    public float y;

    public Point2D() {
        x = (float) 0.0;
        y = (float) 0.0;
    }

    /**
     * The usual dot product.
     * @param pt The other point (vector)
     */
    public float dotProduct(Point2D pt) {
        return (x * pt.x + y * pt.y);
    }

    /**
     * The Euclidean length.
     */
    public float length() {
        return (float) java.lang.Math.sqrt(x * x + y * y);
    }

    /**
     * Correlation between two points (vectors).
     * @param pt The other point (vector)
     */
    public float correlation(Point2D pt) {
        float len1 = this.length();
        float len2 = pt.length();
        if (len1 == (float) 0.0 || len2 == (float) 0.0) {
            return ((float) 0.0);
        } else {
            return (dotProduct(pt) / (len1 * len2));
        }
    }

}