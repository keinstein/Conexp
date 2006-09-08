/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package canvas;

import canvas.highlightstrategies.NullHighlightStrategy;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;


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

    public CanvasScheme makeCopy() {
        DefaultCanvasScheme ret = new DefaultCanvasScheme();
        ret.setColorScheme(getColorScheme().makeCopy());
        ret.setHighlightStrategy(getHighlightStrategy().makeCopy());
        return ret;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DefaultCanvasScheme)) {
            return false;
        }
        return isEqual((CanvasScheme) obj);

    }

    public boolean isEqual(CanvasScheme other) {
        if (null == other) {
            return false;
        }
        if (colorScheme != null ? !colorScheme.equals(other.getColorScheme()) : other.getColorScheme() != null) {
            return false;
        }
        if (highlightStrategy != null ? !highlightStrategy.equals(other.getHighlightStrategy()) : other.getHighlightStrategy() != null)
        {
            return false;
        }

        return true;
    }

    public int hashCode() {
        int result;
        result = colorScheme != null ? colorScheme.hashCode() : 0;
        result = 29 * result + (highlightStrategy != null ? highlightStrategy.hashCode() : 0);
        return result;
    }

    public String toString() {
        return "DefaultCanvasScheme{" +
                "colorScheme=" + colorScheme +
                ", highlightStrategy=" + highlightStrategy +
                '}';
    }
}
