/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor;


public abstract class BasicDrawParams implements DrawParameters {
    protected static final int GAP_X = 80;
    protected static final int GAP_Y = 40;

    protected static final int DEFAULT_GRID_SIZE_X = 80;
    protected static final int MIN_GRID_SIZE_X = 10;
    protected static final int MAX_GRID_SIZE_X = 200;

    protected static final int DEFAULT_GRID_SIZE_Y = 60;
    protected static final int MIN_GRID_SIZE_Y = 10;
    protected static final int MAX_GRID_SIZE_Y = 200;

    protected static final int DEFAULT_MAX_NODE_RADIUS = 12;
    protected static final int MIN_MAX_NODE_RADIUS = 2;
    protected static final int MAX_MAX_NODE_RADIUS = 40;

    protected static final float DEFAULT_MAX_EDGE_STROKE = 2.5f;

    public int getMinGapX() {
        return GAP_X + getMaxNodeRadius();
    }

    public int getMinGapY() {
        return GAP_Y + getMaxNodeRadius();
    }


}
