/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package canvas;

public class CanvasTool extends DefaultTool {
    protected FigureDrawingCanvas figureDrawingCanvas;

    public CanvasTool(FigureDrawingCanvas figureDrawingCanvas) {
        this.figureDrawingCanvas = figureDrawingCanvas;
    }
}
