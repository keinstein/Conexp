/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package canvas;

import java.awt.*;

public interface CanvasColorScheme {
    Color getTextBackColor();

    Color getNodeBorderColor();

    Color getNodeColor();

    Color getEdgeColor();

    Color getNodeFillColor();

    Color getHighlightColor();

    Color getTextColor();

    Color getSelectedTextBackground();
}
