/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package canvas;

import java.awt.Color;

public interface CanvasColorScheme {
    Color getTextBackColor();

    Color getNodeBorderColor();

    Color getNodeColor();

    Color getEdgeColor();

    Color getNodeFillColor();

    Color getCollisionColor();

    Color getHighlightColor();

    Color getTextColor();

    Color getSelectedTextBackground();

    CanvasColorScheme makeCopy();
}
