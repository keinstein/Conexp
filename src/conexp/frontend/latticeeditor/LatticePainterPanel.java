/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor;

import com.visibleworkings.trace.Trace;
import conexp.core.Lattice;
import conexp.core.LatticeStatistics;
import conexp.core.layout.LayoutParameters;
import conexp.frontend.LatticeDrawingProvider;
import conexp.frontend.ResourceLoader;
import conexp.frontend.ViewChangeInterfaceWithConfig;
import conexp.frontend.util.ActionChainUtil;
import conexp.frontend.util.IResourceManager;
import conexp.frontend.util.ResourceManager;
import conexp.util.gui.ToggleAbstractAction;
import util.errorhandling.AppErrorHandler;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.JOptionPane;
import javax.swing.JToggleButton;
import javax.swing.JViewport;
import javax.swing.ToolTipManager;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;


public class LatticePainterPanel extends BaseLatticePainterPane implements ViewChangeInterfaceWithConfig {

    public static final String USE_IDEAL_MOVE_STRATEGY_PROPERTY = "USE_IDEAL_MOVE_STRATEGY_PROPERTY";
    public static final String FIT_TO_SIZE_PROPERTY = "FIT_TO_SIZE_PROPERTY";

    public static LatticePainterPanel createLatticePainterPanel(LatticeDrawingProvider latticeDrawingProvider) {
//        System.out.println("LatticePainterPanel.createLatticePainterPanel");
        LatticePainterPanel ret = new LatticePainterPanel();
        ret.setLatticeSupplier(latticeDrawingProvider);
        return ret;
    }

    public void initialUpdate() {
//        System.out.println("LatticePainterPanel.initialUpdate");
        super.initialUpdate();
    }


    class PanningTool extends canvas.DefaultTool {
        int xDiff = 0;
        int yDiff = 0;

        public void mousePressed(MouseEvent e) {
            setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
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
            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
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

    class SelectMoveModeAction extends ToggleAbstractAction {
        public SelectMoveModeAction() {
            super("selectMoveMode");
        }

        public boolean isSelected() {
            return isUseIdealMoveStrategy();
        }

        public void actionPerformed(ActionEvent e) {
            Object o = e.getSource();
            if (o instanceof JToggleButton) {
                final boolean selected = ((JToggleButton) o).isSelected();
                setUseIdealMoveStrategy(selected);
            }
        }
    }

    class SelectScaleToFitModeAction extends ToggleAbstractAction {
        public SelectScaleToFitModeAction() {
            super("scaleToFitMode");
        }

        public boolean isSelected() {
            return isFitToSize();
        }

        public void actionPerformed(ActionEvent e) {
            Object o = e.getSource();
            if (o instanceof JToggleButton) {
                setFitToSize(((JToggleButton) o).isSelected());
            }
        }
    }

    class ShowLatticeStatisticsAction extends AbstractAction {
        public ShowLatticeStatisticsAction() {
            super("showLatticeStatistics");
        }

        public void actionPerformed(ActionEvent e) {
            showStatistics();
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

    //experimental
    class AssignYCoordsAccordingToWeigthAction extends AbstractAction {
        public AssignYCoordsAccordingToWeigthAction() {
            super("assignYByWeightCoords");
        }

        public void actionPerformed(ActionEvent e) {
            rescaleYByWeight();
        }
    }


    class StorePreferencesAction extends AbstractAction {
        public StorePreferencesAction() {
            super("storePreferences");
        }

        public void actionPerformed(ActionEvent e) {
            storePreferences();
        }
    }

    Preferences preferences = Preferences.userNodeForPackage(LatticePainterPanel.class);

    public Preferences getPreferences() {
        return preferences;
    }

    private void storePreferences() {
        getConceptSetDrawing().storePreferences();
        doStorePreferences();
    }

    private void doStorePreferences() {
        getPreferences().putBoolean(USE_IDEAL_MOVE_STRATEGY_PROPERTY, isUseIdealMoveStrategy());
        getPreferences().putBoolean(FIT_TO_SIZE_PROPERTY, isFitToSize());
    }


    public void restorePreferences() {
        doRestorePreferences();
    }

    private void doRestorePreferences() {
        setUseIdealMoveStrategy(getPreferences().getBoolean(USE_IDEAL_MOVE_STRATEGY_PROPERTY, false));
        setFitToSize(getPreferences().getBoolean(FIT_TO_SIZE_PROPERTY, false));
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

    private void showMessage(final String message) {
        JOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(LatticePainterPanel.this), message);
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

    private LatticePainterLayoutChangeHandler layoutChangeHandler = new LatticePainterLayoutChangeHandler();
    private LatticeDrawParamsEventHandler drawParamsEventHandler = new LatticeDrawParamsEventHandler();

    class LatticePainterLayoutChangeHandler implements java.beans.PropertyChangeListener {
        public void propertyChange(java.beans.PropertyChangeEvent evt) {
            if (evt.getSource() == LatticePainterPanel.this.getPainterOptions()) {
                Trace.gui.eventm("Get message for lattice painter", evt.getPropertyName());
                String propertyName = evt.getPropertyName();
                if (LatticeCanvasDrawStrategiesContextProperties.LAYOUT_PROPERTY.equals(propertyName)) {
                    getLatticeDrawing().layoutLattice();
                }
            }
        }
    }

    ;


    class LatticeDrawParamsEventHandler implements PropertyChangeListener {
        public void propertyChange(PropertyChangeEvent evt) {
            Trace.gui.eventm("Get message for lattice painter", evt.getPropertyName());
            String propertyName = evt.getPropertyName();
            if (DrawParamsProperties.GRID_SIZE_Y_PROPERTY.equals(propertyName)) {
                rescaleByYCoord();
            }
            if (DrawParamsProperties.GRID_SIZE_X_PROPERTY.equals(propertyName)) {
                handleXGridChange(evt);
            }
            if (LatticePainterDrawParams.SHOW_COLLISIONS_PROPERTY.equals(propertyName)) {
                repaint();
            }
        }

        private void handleXGridChange(PropertyChangeEvent event) {
            double coeff = ((Number) event.getNewValue()).doubleValue() / ((Number) event.getOldValue()).doubleValue();
            rescaleByXCoord(coeff);
        }
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

    public LatticePainterPanel() {
        super();
        init();
/*
        getPainterOptions().addPropertyChangeListener(layoutChangeHandler);

        getEditableDrawingParams().addPropertyChangeListener(drawParamsEventHandler);
*/
        addPropertyChangeListener(DRAWING, new DrawingPropertyChangeListener());

        ToolTipManager.sharedInstance().registerComponent(this);

        ActionChainUtil.putActions(getActionChain(), getActions());
    }


    public void setParentActionMap(ActionMap parentActionChain) {
        getActionChain().setParent(parentActionChain);
    }

    /**
     * this function for each lattice node sets up a position, which is nearest to
     * small grid position
     */

    public void alignCoordsToGrid() {
        getDrawing().makeBoundsRectDirty();
        AlignCoordsFigureVisitor alignVisitor = new AlignCoordsFigureVisitor(getPainterOptions().getSmallGridSize());
        visitFiguresAndRepaint(alignVisitor);
    }

    //---------------------------------------------------------------
    public Action[] getActions() {
        Action[] ret = {new AlignToGridAction(),
                new AssignYCoordsAccordingToWeigthAction(),
                new GrabAndDragAction(),
                new ExportLatticeInfoAction("saveImage", "Export diagram as", this, AppErrorHandler.getInstance()),
                new SelectMoveModeAction(),
                new SelectScaleToFitModeAction(),
                new AddZoomAction(),
                new ReduceZoomAction(),
                new NoZoomAction(),
                new StorePreferencesAction(),
                new ShowLatticeStatisticsAction()
        };
        return ret;

    }

    private void showStatistics() {
        showMessage(new LatticeStatistics(getLattice()).getDescriptionString());
    }

    //---------------------------------------------------------------
    public Dimension getMinimumSize() {
        return new Dimension(25, 25);
    }

    //----------------------------------------------
    public static ResourceBundle getResources() {
        return LatticePainterPanel.resources;
    }

    public IResourceManager getResourceManager() {
        return new ResourceManager(getResources());
    }

    public void rescaleYByWeight() {
        Lattice lattice = getLattice();
        double startY = getLatticeDrawing().getFigureForConcept(lattice.getTop()).getCenterY();
        double endY = getLatticeDrawing().getFigureForConcept(lattice.getBottom()).getCenterY();
        double weightFactor = 1.0 / lattice.getOne().getObjCnt();
        visitFiguresAndRepaint(new RescaleByWeigthVisitor(startY, endY, weightFactor));
    }

    public void rescaleByXCoord(double coeff) {
        visitFiguresAndRepaint(new RescaleByXFigureVisitor(getDrawParameters().getMinGapX(), coeff));
    }

    public void rescaleByYCoord() {
        LayoutParameters drawParams = getDrawParameters();
        visitFiguresAndRepaint(new RescaleByYFigureVisitor(0, drawParams.getGridSizeY(), getConceptSetDrawing().getNumberOfLevelsInDrawing()));
    }

    //----------------------------------------------
    public String getToolTipText(MouseEvent evt) {
        return canDescribePoint(evt.getPoint()) ? describeActivePoint() : null;
    }

    //------------------------------------------------------------------
    private class DrawingPropertyChangeListener implements PropertyChangeListener {
        public void propertyChange(PropertyChangeEvent evt) {
            ConceptSetDrawing oldDrawing = (ConceptSetDrawing) evt.getOldValue();
            if (null != oldDrawing) {
                oldDrawing.getPainterOptions().removePropertyChangeListener(layoutChangeHandler);
                getEditableDrawingParams(oldDrawing).removePropertyChangeListener(drawParamsEventHandler);
            }
            ConceptSetDrawing newDrawing = (ConceptSetDrawing) evt.getNewValue();
            if (null != newDrawing) {
                newDrawing.getPainterOptions().addPropertyChangeListener(layoutChangeHandler);
                getEditableDrawingParams(newDrawing).addPropertyChangeListener(drawParamsEventHandler);
            }
        }
    }

}
