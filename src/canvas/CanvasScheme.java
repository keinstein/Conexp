/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package canvas;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

public interface CanvasScheme {
    CanvasColorScheme getColorScheme();

    Font getLabelsFont(Graphics g);

    //todo: think about appropriate place
    FontMetrics getLabelsFontMetrics(Graphics g);

    //todo: remove duplication between canvasScheme.getHighlightStrategy and DrawStrategiesContext.getHighlightStrategy
    IHighlightStrategy getHighlightStrategy();

    CanvasScheme makeCopy();

    boolean isEqual(CanvasScheme other);
}
