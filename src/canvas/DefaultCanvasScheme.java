package canvas;

import canvas.highlightstrategies.NullHighlightStrategy;

/*
 * User: Serhiy Yevtushenko
 * Date: Oct 19, 2002
 * Time: 9:47:02 PM
 */
public class DefaultCanvasScheme implements CanvasScheme{
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


}
