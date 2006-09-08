/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor;

import conexp.core.layout.LayoutParameters;




public interface DrawParameters extends LayoutParameters {

    int getMinGapX();

    int getMinGapY();

    float getMaxEdgeStroke();

    boolean isShowCollisions();

    DrawParameters makeCopy();

}
