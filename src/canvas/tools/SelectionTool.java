/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package canvas.tools;

import canvas.CanvasTool;
import canvas.DefaultToolListener;
import canvas.Figure;
import canvas.FigureDrawingCanvas;
import canvas.Tool;
import canvas.ToolEvent;

import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;


public class SelectionTool extends CanvasTool {
    public SelectionTool(FigureDrawingCanvas figureDrawingCanvas) {
        super(figureDrawingCanvas);
    }

    Tool childTool = null;

    public void mousePressed(MouseEvent e) {
        if (childTool != null) {
            return;
        }
        Point2D worldCoords = figureDrawingCanvas.getWorldCoords(e.getPoint());
        Figure f = figureDrawingCanvas.findFigureInReverseOrderToDrawing(worldCoords.getX(), worldCoords.getY());
        Tool tool;
        if (f != null) {
            tool = makeMoveFigureTool();
        } else {
            tool = makeSelectionTrackerTool();
        }
        childTool = tool;
        figureDrawingCanvas.setActiveTool(childTool);
        childTool.mousePressed(e);
    }

    private SelectionTrackerTool makeSelectionTrackerTool() {
        SelectionTrackerTool tool = new SelectionTrackerTool(figureDrawingCanvas);
        tool.addToolListener(new ActivateMeOnDeactivateToolListener());
        return tool;
    }

    private MoveNodeTool makeMoveFigureTool() {
        MoveNodeTool tool = new MoveNodeTool(figureDrawingCanvas);
        tool.addToolListener(new ActivateMeOnDeactivateToolListener());
        return tool;
    }

    private class ActivateMeOnDeactivateToolListener extends DefaultToolListener {
        protected void onToolDeactivated(ToolEvent toolEvent) {
            childTool.removeToolListener(this);
            childTool = null;
            figureDrawingCanvas.setActiveTool(SelectionTool.this);
        }
    }
}
