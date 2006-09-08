/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor;

import canvas.CanvasScheme;
import com.visibleworkings.trace.Trace;
import conexp.frontend.latticeeditor.movestrategies.FigureIdealMoveStrategy;
import conexp.frontend.latticeeditor.movestrategies.OneFigureMoveStrategy;

import javax.swing.JComponent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class BaseConceptSetCanvas extends LatticeCanvas {
    protected CanvasScheme makeDefaultCanvasScheme() {
        return new LatticePainterOptions();
    }

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
                if (LatticeCanvasSchemeProperties.LABELS_FONT_SIZE_PROPERTY.equals(propertyName)) {
                    repaint();
                }
            }
        }
    }


    public BaseConceptSetCanvas() {
        super();
    }


    protected void init() {
        setFigureMoveStrategyAccordingToOptions();
        addOptionsListener();
    }

    private void addOptionsListener() {
        getPainterOptions().addPropertyChangeListener(eventHandler);
        addPropertyChangeListener(CANVAS_SCHEME_PROPERTY, new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                //assert CANVAS_SCHEME_PROPERTY.equals(evt.getPropertyName());
                LatticePainterOptions oldOptions = (LatticePainterOptions) evt.getOldValue();
                if (oldOptions != null) {
                    oldOptions.removePropertyChangeListener(eventHandler);
                }
                LatticePainterOptions newOptions = (LatticePainterOptions) evt.getNewValue();
                if (null != newOptions) {
                    newOptions.addPropertyChangeListener(eventHandler);
                }
            }
        });
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
        final ConceptSetDrawing conceptSetDrawing = getConceptSetDrawing();
        return getEditableDrawingParams(conceptSetDrawing);
    }

    protected static LatticePainterDrawParams getEditableDrawingParams(final ConceptSetDrawing conceptSetDrawing) {
        return conceptSetDrawing.getEditableDrawParameters();
    }
}
