/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor;

import conexp.core.layout.DefaultLayoutParameters;


public class BasicDrawParams extends DefaultLayoutParameters implements DrawParameters {
    protected static final int GAP_X = 80;
    protected static final int GAP_Y = 40;

    protected static final float DEFAULT_MAX_EDGE_STROKE = 4.0f;

    protected static boolean DEFAULT_SHOW_COLLISIONS = true;

    protected BasicDrawParams() {
    }

    static final DrawParameters g_Instance = new BasicDrawParams();

    public static DrawParameters getInstance() {
        return g_Instance;
    }

    public int getMinGapX() {
        return GAP_X + getMaxNodeRadius();
    }

    public int getMinGapY() {
        return GAP_Y + getMaxNodeRadius();
    }


    public float getMaxEdgeStroke() {
        return DEFAULT_MAX_EDGE_STROKE;
    }

    public boolean isShowCollisions() {
        return DEFAULT_SHOW_COLLISIONS;
    }

    public DrawParameters makeCopy() {
        return getInstance();
    }
}
