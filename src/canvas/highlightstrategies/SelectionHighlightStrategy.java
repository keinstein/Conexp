package canvas.highlightstrategies;

import canvas.Figure;
import canvas.FigureDrawingCanvas;
import canvas.IHighlightStrategy;

/*
 * User: Serhiy Yevtushenko
 * Date: Oct 19, 2002
 * Time: 10:09:33 PM
 */
public class SelectionHighlightStrategy implements IHighlightStrategy{
    FigureDrawingCanvas drawingEditor;

    public SelectionHighlightStrategy(FigureDrawingCanvas drawingEditor) {
        this.drawingEditor = drawingEditor;
    }

    public boolean highlightFigure(Figure figure) {
        return drawingEditor.getSelection().contains(figure);
    }

    public boolean isActive() {
        return drawingEditor.hasSelection();
    }
}
