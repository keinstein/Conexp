/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



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
