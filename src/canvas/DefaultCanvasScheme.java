/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package canvas;

import canvas.highlightstrategies.NullHighlightStrategy;

import java.awt.*;


public class DefaultCanvasScheme implements CanvasScheme {
    CanvasColorScheme colorScheme = new DefaultColorScheme();

    public CanvasColorScheme getColorScheme() {
        return colorScheme;
    }

    public void setColorScheme(CanvasColorScheme colorScheme) {
        this.colorScheme = colorScheme;
    }


    IHighlightStrategy highlightStrategy = NullHighlightStrategy.getInstance();

    public IHighlightStrategy getHighlightStrategy() {
        return highlightStrategy;
    }

    public void setHighlightStrategy(IHighlightStrategy highlightStrategy) {
        this.highlightStrategy = highlightStrategy;
    }

    public Font getLabelsFont(Graphics g) {
        return g.getFont();
    }

    public FontMetrics getLabelsFontMetrics(Graphics g) {
        return g.getFontMetrics();
    }


}
