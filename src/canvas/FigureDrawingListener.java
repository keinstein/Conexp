/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package canvas;

import java.awt.Dimension;
import java.util.EventListener;


public interface FigureDrawingListener extends EventListener {

    void dimensionChanged(Dimension newDim);

    void needUpdate();
}
