/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor;


/**
 * todo: specify responsibilities division
 * between DrawParams, and CanvasScheme
 */

public interface DrawParameters {
    int getGridSizeX();

    int getGridSizeY();

    int getMinGapX();

    int getMinGapY();

    float getMaxEdgeStroke();

    int getMinNodeRadius();

    int getMaxNodeRadius();
}
