/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/

package conexp.frontend.latticeeditor;

import canvas.util.SaveImageAction;
import com.visibleworkings.trace.Trace;
import conexp.core.Lattice;
import conexp.frontend.LatticeDrawingProvider;
import conexp.frontend.ResourceLoader;
import conexp.frontend.ViewChangeInterfaceWithConfig;
import conexp.frontend.util.ActionChainUtil;
import conexp.frontend.util.IResourceManager;
import conexp.frontend.util.ResourceManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.ResourceBundle;


public class LatticePainterPanel extends BaseConceptSetCanvas implements ViewChangeInterfaceWithConfig {

    protected LatticeDrawing getLatticeDrawing() {
        return (LatticeDrawing) getConceptSetDrawing();
    }


    public Lattice getLattice() {
        return getLatticeDrawing().getLattice();
    }

    class PanningTool extends canvas.DefaultTool {
        int xDiff = 0;
        int yDiff = 0;

        public void mousePressed(MouseEvent e) {
            setCursor(Cursor.getPredefinedCursor(
                    Cursor.MOVE_CURSOR));
            xDiff = e.getX();
            yDiff = e.getY();
        }

        public void mouseDragged(MouseEvent e) {
            Component c = getParent();
            if (c instanceof JViewport) {
                JViewport jv = (JViewport) c;
                Point p = jv.getViewPosition();
                int newX = p.x - (e.getX() - xDiff);
                int newY = p.y - (e.getY() - yDiff);

                int maxX = getWidth() - jv.getWidth();
                int maxY = getHeight() - jv.getHeight();
                if (newX < 0) {
                    newX = 0;
                }
                if (newX > maxX) {
                    newX = maxX;
                }
                if (newY < 0) {
                    newY = 0;
                }
                if (newY > maxY) {
                    newY = maxY;
                }
                jv.setViewPosition(new Point(newX, newY));
            }
        }

        public void mouseReleased(MouseEvent e) {
            setCursor(Cursor.getPredefinedCursor(
                    Cursor.DEFAULT_CURSOR));
        }


    }

    canvas.Tool PANNING_TOOL = new PanningTool();

    //---------------------------------------------
    class AlignToGridAction extends AbstractAction {
        AlignToGridAction() {
            super("alignToGrid");
        }

        public void actionPerformed(ActionEvent e) {
            alignCoordsToGrid();
        }
    }

    //---------------------------------------------
    class GrabAndDragAction extends AbstractAction {
        GrabAndDragAction() {
            super("grabAndDrag");
        }

        public void actionPerformed(ActionEvent e) {
            Object o = e.getSource();
            if (o instanceof JToggleButton) {
                setScrollMode(((JToggleButton) o).isSelected());
                if (isScrollMode()) {
                    setActiveTool(PANNING_TOOL);
                } else {
                    deactivateTool();
                }
            }
        }
    }

    class SelectMoveModeAction extends AbstractAction {
        public SelectMoveModeAction() {
            super("selectMoveMode");
        }

        public void actionPerformed(ActionEvent e) {
            Object o = e.getSource();
            if (o instanceof JToggleButton) {
                final boolean selected = ((JToggleButton) o).isSelected();
                setUseIdealMoveStrategy(selected);
            }
        }

    }


    class SelectScaleToFitModeAction extends AbstractAction {
        public SelectScaleToFitModeAction() {
            super("scaleToFitMode");
        }

        public void actionPerformed(ActionEvent e) {
            Object o = e.getSource();
            if (o instanceof JToggleButton) {
                setFitToSize(((JToggleButton) o).isSelected());
            }
        }
    }

    class AddZoomAction extends AbstractAction {
        public AddZoomAction() {
            super("addZoom");
        }

        public void actionPerformed(ActionEvent e) {
            setZoom(getZoom() * 1.1);
        }
    }

    class ReduceZoomAction extends AbstractAction {
        public ReduceZoomAction() {
            super("reduceZoom");
        }

        public void actionPerformed(ActionEvent e) {
            setZoom(getZoom() / 1.1);
        }
    }

    class NoZoomAction extends AbstractAction {
        public NoZoomAction() {
            super("noZoom");
        }

        public void actionPerformed(ActionEvent e) {
            setZoom(1.0);
        }
    }


    private boolean scrollMode = false;

    protected boolean isScrollMode() {
        return scrollMode;
    }

    protected void setScrollMode(boolean scrollMode) {
        this.scrollMode = scrollMode;
    }

    private static ResourceBundle resources;

    //---------------------------------------------------------------
    static {
        resources = ResourceLoader.getResourceBundle("conexp/frontend/resources/LatticePainterPanel");
    }

    private LatticePainterLocalEventHandler localEventHandler = new LatticePainterLocalEventHandler();


    class LatticePainterLocalEventHandler implements java.beans.PropertyChangeListener {
        public void propertyChange(java.beans.PropertyChangeEvent evt) {
            if (evt.getSource() == LatticePainterPanel.this.getPainterOptions()) {
                Trace.gui.eventm("Get message for lattice painter", evt.getPropertyName());
                String propertyName = evt.getPropertyName();
                if (propertyName.equals("gridSizeY")) {
                    rescaleByYCoord();
                }
                if (propertyName.equals("gridSizeX")) {
                    handleXGridChange(evt);
                }
                if (propertyName.equals("layout")) {
                    getLatticeDrawing().layoutLattice();
                }
            }
        }

        private void handleXGridChange(java.beans.PropertyChangeEvent arg1) {
            double coeff = ((Number) arg1.getNewValue()).doubleValue() / ((Number) arg1.getOldValue()).doubleValue();
            rescaleByXCoord(coeff);
        }
    };


    LatticeDrawingProvider latticeSupplier;

    public LatticeDrawingProvider getLatticeSupplier() {
        return latticeSupplier;
    }

    //---------------------------------------------------------------
    private ActionMap actionChain = new ActionMap();

//---------------------------------------------
    public Component getViewComponent() {
        return this;
    }
//---------------------------------------------

    public ActionMap getActionChain() {
        return actionChain;
    }

    public LatticePainterPanel(LatticeDrawingProvider supplier) {
        super();
        setLatticeSupplier(supplier);
        init();
        getPainterOptions().addPropertyChangeListener(localEventHandler);

        ToolTipManager.sharedInstance().registerComponent(this);
        ActionChainUtil.putActions(getActionChain(), getActions());
    }

    private void setLatticeSupplier(LatticeDrawingProvider supplier) {
        latticeSupplier = supplier;
        setOptions(latticeSupplier.getDrawing().getOptions());
    }

    public void setParentActionMap(ActionMap parentActionChain) {
        getActionChain().setParent(parentActionChain);
    }

    /**
     *  this function for each lattice node sets up a position, which is nearest to
     *  small grid position
     */

    public void alignCoordsToGrid() {
        getDrawing().makeBoundsRectDirty();
        AlignCoordsFigureVisitor alignVisitor = new AlignCoordsFigureVisitor(getPainterOptions().getSmallGridSize());
        visitFiguresAndRepaint(alignVisitor);
    }

    //---------------------------------------------------------------
    public Action[] getActions() {
        Action[] ret = {new AlignToGridAction(),
                        new GrabAndDragAction(),
                        new SaveImageAction(this, "Save lattice image as"),
                        new SelectMoveModeAction(),
                        new SelectScaleToFitModeAction(),
                        new AddZoomAction(),
                        new ReduceZoomAction(),
                        new NoZoomAction()};
        return ret;

    }

    //---------------------------------------------------------------
    public Dimension getMinimumSize() {
        return new Dimension(25, 25);
    }

    //----------------------------------------------
    public ResourceBundle getResources() {
        return LatticePainterPanel.resources;
    }

    public IResourceManager getResourceManager() {
        return new ResourceManager(getResources());
    }

    public void rescaleByXCoord(double coeff) {
        visitFiguresAndRepaint(new RescaleByXFigureVisitor(getDrawParameters().getMinGapX(), coeff));
    }

    public void rescaleByYCoord() {
        DrawParameters drawParams = getDrawParameters();
        visitFiguresAndRepaint(new RescaleByYFigureVisitor(drawParams.getMinGapY(), drawParams.getGridSizeY(), getConceptSetDrawing().getNumberOfLevelsInDrawing()));
    }

    //----------------------------------------------
    public String getToolTipText(MouseEvent evt) {
        return canDescribePoint(evt.getPoint()) ? describeActivePoint() : null;
    }
    //------------------------------------------------------------------

    public void initialUpdate() {
        setConceptSetDrawing(getLatticeSupplier().getDrawing());
    }

}
