package canvas;
/*
 * User: Serhiy Yevtushenko
 * Date: Oct 18, 2002
 * Time: 6:49:57 PM
 */
public class CanvasTool extends DefaultTool {
    protected FigureDrawingCanvas figureDrawingCanvas;

    public CanvasTool(FigureDrawingCanvas figureDrawingCanvas) {
        this.figureDrawingCanvas = figureDrawingCanvas;
    }
}
