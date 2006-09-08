/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package canvas.highlightstrategies;

import canvas.Figure;
import canvas.FigureDrawingCanvas;
import canvas.IHighlightStrategy;
import util.Assert;


public class SelectionHighlightStrategy implements IHighlightStrategy {
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

    public IHighlightStrategy makeCopy() {
        Assert.notImplemented();
        return null;
    }

    public String toString() {
        return "SelectionHighlightStrategy{}";
    }
}
