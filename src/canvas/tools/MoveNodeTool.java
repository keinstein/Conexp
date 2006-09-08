/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package canvas.tools;

import canvas.CanvasTool;
import canvas.Figure;
import canvas.FigureDrawingCanvas;

import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;


public class MoveNodeTool extends CanvasTool {
    Point2D startPoint;

    public MoveNodeTool(FigureDrawingCanvas figureDrawingCanvas) {
        super(figureDrawingCanvas);
    }

    public void mousePressed(MouseEvent e) {
        Point2D userCoords = figureDrawingCanvas.getWorldCoords(e.getPoint());
        Figure figure = figureDrawingCanvas.findFigureInReverseOrderToDrawing(userCoords.getX(),
                userCoords.getY());
        if (!figureDrawingCanvas.hasSelection() || !figureDrawingCanvas.getSelection().contains(figure)) {
            figureDrawingCanvas.selectFigure(figure);
        }
        //todo: think and clean based on experience
        if (figure != null && SwingUtilities.isRightMouseButton(e)) {
            JPopupMenu popupMenu = new JPopupMenu();
            figureDrawingCanvas.fillPopupMenu(popupMenu, figure);
            if (popupMenu.getComponentCount() != 0) {
                popupMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        }

        if (figureDrawingCanvas.hasSelection()) {
            startPoint = userCoords;
        }
    }

    public void mouseDragged(MouseEvent e) {
        doMoveFigures(e.getPoint());
    }

    private void doMoveFigures(final Point point) {
        if (figureDrawingCanvas.hasSelection()) {
            Point2D userCoords = figureDrawingCanvas.getWorldCoords(point);
            //may be here use screen coordinates?
            if (figureDrawingCanvas.isDiscernibleDifference(startPoint, userCoords)) {
                final double dx = userCoords.getX() - startPoint.getX();
                final double dy = userCoords.getY() - startPoint.getY();
                figureDrawingCanvas.moveFigures(figureDrawingCanvas.getSelection(), dx, dy);
                figureDrawingCanvas.updateDrawing();
                //user coords of point can change after moving figure
                startPoint = figureDrawingCanvas.getWorldCoords(point);
                figureDrawingCanvas.repaint();
            }
        }
    }


    public void mouseReleased(MouseEvent e) {
        doMoveFigures(e.getPoint());
        deactivate();
    }

}
