/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package canvas.tools;

import canvas.CanvasTool;
import canvas.FigureDrawingCanvas;
import canvas.figures.TrackerRectangularFigure;
import util.gui.GraphicObjectsFactory;

import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;


public class SelectionTrackerTool extends CanvasTool {
    private Point2D start;


    TrackerRectangularFigure figure;
    Rectangle2D selectionRect;


    public SelectionTrackerTool(FigureDrawingCanvas figureDrawingCanvas) {
        super(figureDrawingCanvas);
    }

    public void mousePressed(MouseEvent e) {
        start = figureDrawingCanvas.getWorldCoords(e.getPoint());
        selectionRect = GraphicObjectsFactory.makeRectangle2D(start.getX(), start.getY(), 0, 0);
        figure = new TrackerRectangularFigure(selectionRect);
        figureDrawingCanvas.getDrawing().addForegroundFigure(figure);
    }

    public void mouseDragged(MouseEvent e) {
        Point2D current = figureDrawingCanvas.getWorldCoords(e.getPoint());
        updateRectangle(current);
        figure.setRectangle(selectionRect);


        figureDrawingCanvas.repaint();
    }

    public void mouseReleased(MouseEvent e) {
        Point2D current = figureDrawingCanvas.getWorldCoords(e.getPoint());
        updateRectangle(current);

        figureDrawingCanvas.getDrawing().removeForegroundFigure(figure);
        figureDrawingCanvas.selectFiguresInRect(selectionRect);
        figureDrawingCanvas.repaint();

        deactivate();
    }

    private void updateRectangle(Point2D current) {
        selectionRect = GraphicObjectsFactory.makeRectangle2D(Math.min(start.getX(), current.getX()), Math.min(start.getY(),
                current.getY()), Math.abs(start.getX() - current.getX()), Math.abs(start.getY() - current.getY()));
    }


}
