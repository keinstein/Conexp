/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package canvas;


public interface FigureDrawingListener extends java.util.EventListener {

    void dimensionChanged(java.awt.Dimension newDim);

    void needUpdate();
}
