/*
 * User: sergey
 * Date: Jan 27, 2002
 * Time: 3:41:28 PM
 */
package conexp.frontend.latticeeditor;

public class DefaultDrawParams extends BasicDrawParams {
    public int getGridSizeX() {
        return DEFAULT_GRID_SIZE_X;
    }

    public int getGridSizeY() {
        return DEFAULT_GRID_SIZE_Y;
    }

    public int getMinNodeRadius() {
        return MIN_MAX_NODE_RADIUS;
    }

    public int getMaxNodeRadius() {
        return DEFAULT_MAX_NODE_RADIUS;
    }

    public float getMaxEdgeStroke() {
        return DEFAULT_MAX_EDGE_STROKE;
    }

}
