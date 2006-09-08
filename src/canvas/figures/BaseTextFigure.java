/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package canvas.figures;

import canvas.CanvasColorScheme;
import canvas.CanvasScheme;
import canvas.IHighlightStrategy;

import java.awt.Color;

public abstract class BaseTextFigure extends RectangularFigure {


    ColorTransformer colorTransformer = DefaultColorTransformer.getInstance();

    public void setColorTransformer(ColorTransformer colorTransformer) {
        this.colorTransformer = colorTransformer;
    }

    protected Color getTextColor(CanvasScheme opt) {
        IHighlightStrategy highlightStrategy = opt.getHighlightStrategy();
        Color textColor = opt.getColorScheme().getTextColor();
        return colorTransformer.getColor(textColor, textColor, shouldHighlight(highlightStrategy), highlightStrategy.isActive());
    }

    protected Color getBorderColor(CanvasScheme opt) {
        IHighlightStrategy highlightStrategy = opt.getHighlightStrategy();
        CanvasColorScheme colorScheme = opt.getColorScheme();
        return colorTransformer.getColor(colorScheme.getHighlightColor(), colorScheme.getEdgeColor(),
                shouldHighlight(highlightStrategy), highlightStrategy.isActive());
    }

    protected boolean shouldHighlight(IHighlightStrategy highlightStrategy) {
        return false;
    }

    protected Color getBackground(CanvasScheme opt) {
        return opt.getColorScheme().getTextBackColor();
    }
}
