/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package canvas;


public interface CanvasScheme {
    CanvasColorScheme getColorScheme();

    IHighlightStrategy getHighlightStrategy();
}
