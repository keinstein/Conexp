/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package canvas;

import java.awt.*;

public interface CanvasScheme {
    CanvasColorScheme getColorScheme();

    Font getLabelsFont(Graphics g);

    //todo: think about appropriate place
    FontMetrics getLabelsFontMetrics(Graphics g);

    //todo: remove duplication between canvasScheme.getHighlightStrategy and DrawStrategiesContext.getHighlightStrategy
    IHighlightStrategy getHighlightStrategy();

    CanvasScheme makeCopy();
}
