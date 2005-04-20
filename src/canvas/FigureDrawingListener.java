/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package canvas;

import java.awt.geom.Dimension2D;
import java.awt.*;


public interface FigureDrawingListener extends java.util.EventListener {

    void dimensionChanged(Dimension newDim);

    void needUpdate();
}
