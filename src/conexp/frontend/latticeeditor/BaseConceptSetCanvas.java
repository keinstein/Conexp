/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor;

import com.visibleworkings.trace.Trace;
import conexp.frontend.latticeeditor.movestrategies.FigureIdealMoveStrategy;
import conexp.frontend.latticeeditor.movestrategies.OneFigureMoveStrategy;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class BaseConceptSetCanvas extends LatticeCanvas {

    class LatticePainterGenericEventHandler implements PropertyChangeListener {
        public void propertyChange(PropertyChangeEvent evt) {
            if (evt.getSource() == getPainterOptions()) {
                Trace.gui.eventm("Get message for lattice painter", evt.getPropertyName());
                String propertyName = evt.getPropertyName();
                if (LatticeCanvasDrawStrategiesContextProperties.NODE_DRAW_STRATEGY_PROPERTY.equals(propertyName) ||
                     LatticeCanvasDrawStrategiesContextProperties.EDGE_DRAW_STRATEGY_PROPERTY.equals(propertyName) ||
                     DrawParamsProperties.MAX_NODE_RADIUS_PROPERTY.equals(propertyName)) {
                    refresh();
                }

                if (LatticeCanvasDrawStrategiesContextProperties.HIGHLIGHT_STRATEGY_PROPERTY.equals(propertyName)) {
                    repaint();
                }
                if(LatticeCanvasSchemeProperties.LABELS_FONT_SIZE_PROPERTY.equals(propertyName)){
                    repaint();
                }
            }
        }
    }


    public BaseConceptSetCanvas(LatticeCanvasScheme latticeCanvasScheme) {
        super(latticeCanvasScheme);
    }

    protected void init() {
        setFigureMoveStrategyAccordingToOptions();
        getPainterOptions().addPropertyChangeListener(eventHandler);
    }


    public void refresh() {
        repaint();
        revalidate();
        getConceptSetDrawing().drawingParametersChanged();

    }


    protected LatticePainterGenericEventHandler eventHandler = new LatticePainterGenericEventHandler();
    boolean useIdealMoveStrategy;
    MoveStrategy ordinalMoveStrategy = new OneFigureMoveStrategy();
    MoveStrategy idealMoveStrategy = new FigureIdealMoveStrategy();
    PainterOptionsPaneEditor optionsEditor;

    public boolean isUseIdealMoveStrategy() {
        return useIdealMoveStrategy;
    }

    public void setUseIdealMoveStrategy(final boolean selected) {
        useIdealMoveStrategy = selected;
        setFigureMoveStrategyAccordingToOptions();
    }

    protected void setFigureMoveStrategyAccordingToOptions() {
        setFigureMoveStrategy(useIdealMoveStrategy ? idealMoveStrategy : ordinalMoveStrategy);
    }

    public JComponent getViewOptions() {
        if (null == optionsEditor) {
            optionsEditor = new PainterOptionsPaneEditor(getPainterOptions(),
                    getEditableDrawingParams(),
                    getConceptSetDrawing().getLabelingStrategiesParams());
        }
        return optionsEditor;
    }

    public LatticePainterDrawParams getEditableDrawingParams() {
        return getConceptSetDrawing().getLatticeDrawingOptions().getEditableDrawingOptions();
    }
}
