/*
 * User: Serhiy Yevtushenko
 * Date: Jun 17, 2002
 * Time: 11:07:30 PM
 */
package conexp.frontend.latticeeditor;

import com.visibleworkings.trace.Trace;
import conexp.frontend.latticeeditor.movestrategies.FigureIdealMoveStrategy;
import conexp.frontend.latticeeditor.movestrategies.OneFigureMoveStrategy;

import javax.swing.*;

public class BaseConceptSetCanvas extends LatticeCanvas {
    class LatticePainterGenericEventHandler implements java.beans.PropertyChangeListener {
        public void propertyChange(java.beans.PropertyChangeEvent evt) {
            if (evt.getSource() == getPainterOptions()) {
                Trace.gui.eventm("Get message for lattice painter", evt.getPropertyName());
                String propertyName = evt.getPropertyName();
                if (propertyName.equals("maxNodeRadius") ||
                        propertyName.equals("nodeDrawStrategy") ||
                        propertyName.equals("edgeDrawStrategy")) {
                    refresh();
                }
                if (propertyName.equals("highlightStrategy")) {
                    repaint();
                }
            }
        }
    };

    public BaseConceptSetCanvas() {
        super();
    }

    protected void init() {
        setFigureMoveStrategyAccordingToOptions();
        getPainterOptions().addPropertyChangeListener(eventHandler);
    }

    public void refresh() {
        repaint();
        revalidate();
    }


    protected LatticePainterGenericEventHandler eventHandler = new LatticePainterGenericEventHandler();
    boolean useIdealMoveStrategy;
    MoveStrategy ordinalMoveStrategy = new OneFigureMoveStrategy();
    MoveStrategy idealMoveStrategy = new FigureIdealMoveStrategy();
    PainterOptionsPaneEditor optionsEditor;

    public void setUseIdealMoveStrategy(final boolean selected) {
        useIdealMoveStrategy = selected;
        setFigureMoveStrategyAccordingToOptions();
    }

    protected void setFigureMoveStrategyAccordingToOptions() {
        setFigureMoveStrategy(useIdealMoveStrategy? idealMoveStrategy : ordinalMoveStrategy);
    }

    public JComponent getViewOptions() {
        if (null == optionsEditor){
            optionsEditor = new PainterOptionsPaneEditor(getPainterOptions(),
                    getConceptSetDrawing().getLabelingStrategiesParams());
        }
        return optionsEditor;
    }
}
