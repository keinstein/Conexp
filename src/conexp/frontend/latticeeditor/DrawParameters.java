/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor;

import conexp.core.layout.LayoutParameters;


/**
 * todo: specify responsibilities division
 * between DrawParams, and CanvasScheme
 */

public interface DrawParameters extends LayoutParameters {

    int getMinGapX();

    int getMinGapY();

    float getMaxEdgeStroke();

    DrawParameters makeCopy();

}
