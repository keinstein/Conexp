/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor;

import canvas.DefaultTool;
import canvas.Figure;
import canvas.FigureBlock;
import canvas.FigureDrawingCanvas;
import canvas.Tool;
import canvas.figures.BorderCalculatingFigure;
import canvas.figures.LineFigure;
import canvas.util.ToolAction;
import conexp.frontend.latticeeditor.figures.ConnectorEndFigure;
import util.collection.CollectionFactory;

import javax.swing.Action;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

public abstract class DiagramEditorPanel extends FigureDrawingCanvas {

    /**
     * todo: remove connectors figures, when one from connected figures are removed from conceptSetDrawing
     */

    public Action[] getActions() {
        return new Action[]{
                new MoveNodeAction(),
                new AddNewNodeAction(),
                new DeleteFigureAction(),
                new ConnectFigureAction(),
        };
    }


    class AddNewNodeAction extends ToolAction {
        public AddNewNodeAction() {
            super(DiagramEditorPanel.this, "addNewNode", "Add", ADD_NEW_NODE_TOOL);
        }
    }

    class MoveNodeAction extends ToolAction {
        public MoveNodeAction() {
            super(DiagramEditorPanel.this, "moveNode", "Move", SELECTION_TOOL);
        }
    }

    class DeleteFigureAction extends ToolAction {
        public DeleteFigureAction() {
            super(DiagramEditorPanel.this, "deleteFigure", "Delete", DELETE_FIGURE_TOOL);
        }
    }

    class ConnectFigureAction extends ToolAction {
        public ConnectFigureAction() {
            super(DiagramEditorPanel.this, "connectFigure", "Connect", CONNECT_FIGURE_TOOL);
        }
    }


    Tool ADD_NEW_NODE_TOOL = new AddNewNodeTool();
    Tool DELETE_FIGURE_TOOL = new DeleteFigureTool();
    Tool CONNECT_FIGURE_TOOL = new ConnectFiguresTool();


    class AddNewNodeTool extends DefaultTool {
        public void mouseReleased(MouseEvent e) {
            Point pt = e.getPoint();
            Point2D userCoords = getWorldCoords(pt);

            Figure figure = makeHierarchyNodeFigure(userCoords);
            if (null != figure) {
                addFigure(figure);
            }
            deactivate();
        }

    }

    protected abstract Figure makeHierarchyNodeFigure(Point2D userCoords);

    class DeleteFigureTool extends DefaultTool {
        public void mousePressed(MouseEvent e) {
            Point2D userCoords = getWorldCoords(e.getPoint());
            Figure f = getDrawing().findFigureInReverseOrder(userCoords.getX(),
                    userCoords.getY());
            if (null != f) {
                getDrawing().removeFigure(f);
                repaint();
                deactivateTool();
            }

        }
    }

    class ConnectFiguresTool extends DefaultTool {
        BorderCalculatingFigure startFigure;

        LineFigure connectorFigure;
        BorderCalculatingFigure connectorEndFigure;

        boolean hasStartFigure() {
            return startFigure != null;
        }

        public void mouseReleased(MouseEvent e) {
            Point2D userCoords = getWorldCoords(e.getPoint());
            Figure f = getDrawing().findFigureInReverseOrderExceptFor(userCoords.getX(), userCoords.getY(),
                    connectorFigure);

            if (f != null) {
                if (isConnectable(f)) {
                    BorderCalculatingFigure connectableFigure = (BorderCalculatingFigure) f;
                    if (hasStartFigure()) {
                        //do connect f and start figure
                        if (connectorFigure.canConnect(startFigure, connectableFigure)) {

                            connectorFigure.setEndFigure(connectableFigure);
                            updateDrawing();
                            repaint();

                            resetState();
                        }
                    } else {
                        startFigure = connectableFigure;
                        connectorEndFigure = new ConnectorEndFigure();
                        connectorEndFigure.setCoords(userCoords.getX(), userCoords.getY());
                        this.connectorFigure = makeConnectorFigure(startFigure, connectorEndFigure);
                        getDrawing().addFigure(connectorFigure);
                        getDrawing().applyChanges();
                        repaint();
                    }
                }
            } else {
                if (hasStartFigure()) {
                    getDrawing().removeFigure(connectorFigure);
                }
                resetState();
                repaint();
            }
        }

        protected boolean isConnectable(Figure f) {
            return f instanceof BorderCalculatingFigure;
        }


        public void deactivate() {
            if (null != connectorFigure) {
                getDrawing().removeFigure(connectorFigure);
                resetState();
            }
            super.deactivate();
        }

        private void resetState() {
            startFigure = null;
            connectorFigure = null;
            connectorEndFigure = null;
        }


        public void mouseMoved(MouseEvent e) {
            if (hasStartFigure()) {
                Point2D userCoords = getWorldCoords(e.getPoint());
                connectorEndFigure.setCoords(userCoords.getX(), userCoords.getY());
                getDrawing().applyChanges();
                repaint();
            }
        }

    }

    protected DiagramEditorPanel() {

    }

    protected static LineFigure makeConnectorFigure(BorderCalculatingFigure startFigure, BorderCalculatingFigure endFigure) {
        LineFigure connectorFigure = new LineFigure(startFigure, endFigure);
        connectorFigure.setSelectable(true);
        connectorFigure.setStartFigure(startFigure);
        connectorFigure.setEndFigure(endFigure);
        return connectorFigure;
    }

    protected void addFigure(Figure figure) {
        getDrawing().addFigure(figure);
        repaint();
    }


    protected java.util.List collectEdges() {
        final java.util.List lines = CollectionFactory.createDefaultList();
        getDrawing().forAllFigures(new FigureBlock() {
            public void exec(Figure f) {
                if (isEdgeFigure(f)) {
                    lines.add(f);
                }
            }
        });
        return lines;
    }

    protected boolean isEdgeFigure(Figure f) {
        return f instanceof LineFigure;
    }

    protected java.util.List collectNodes() {
        final java.util.List nodes = CollectionFactory.createDefaultList();

        getDrawing().forAllFigures(new FigureBlock() {
            public void exec(Figure f) {
                if (isNodeFigure(f)) {
                    nodes.add(f);
                }
            }
        });
        return nodes;
    }

    protected abstract boolean isNodeFigure(Figure f);

}
