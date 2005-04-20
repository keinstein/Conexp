package conexp.core.layout;

/**
 * Created by IntelliJ IDEA.
 * User: sergey
 * Date: 7/4/2005
 * Time: 23:26:51
 * To change this template use File | Settings | File Templates.
 */
public class DefaultLayoutParameters extends BasicLayoutParameters {
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
}
