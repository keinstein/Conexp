/*
 * User: Serhiy Yevtushenko
 * Date: Oct 20, 2002
 * Time: 6:53:12 PM
 */

package canvas.figures;

import canvas.CanvasScheme;
import canvas.IHighlightStrategy;

import java.awt.Color;

public abstract class BaseTextFigure extends RectangularFigure {


    ColorTransformer colorTransformer=DefaultColorTransformer.getInstance();

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
        canvas.CanvasColorScheme colorScheme = opt.getColorScheme();
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
