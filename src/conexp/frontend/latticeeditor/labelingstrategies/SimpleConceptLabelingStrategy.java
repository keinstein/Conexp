/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/

package conexp.frontend.latticeeditor.labelingstrategies;

import canvas.Figure;
import canvas.figures.TextFigure;
import conexp.core.LatticeElement;
import conexp.frontend.latticeeditor.ConceptQuery;
import conexp.frontend.latticeeditor.ConceptSetDrawing;
import conexp.frontend.latticeeditor.DrawParameters;
import conexp.frontend.latticeeditor.figures.AbstractConceptCorrespondingFigure;
import conexp.frontend.latticeeditor.figures.SimpleTextFigure;


public abstract class SimpleConceptLabelingStrategy extends GenericLabelingStrategy {
    protected conexp.core.ExtendedContextEditingInterface cxt;

    public SimpleConceptLabelingStrategy(DrawParameters opt) {
        super(opt);
    }

    protected abstract String getDescriptionString(ConceptQuery conceptQuery);


    public void setContext(conexp.core.ExtendedContextEditingInterface cxt) {
        this.cxt = cxt;
    }

    protected Object makeConnectedObject(ConceptSetDrawing drawing, AbstractConceptCorrespondingFigure f) {
        double angle = 0.5 * Math.PI;
        double offX = opt.getGridSizeX() / 2 * Math.cos(angle);
        double offY = opt.getGridSizeY() / 3 * Math.sin(angle);
        int newX = (int) (f.getCenterX() + offX);
        int newY = (int) (f.getCenterY() + offY);

        LatticeElement concept = f.getConcept();
        TextFigure tf = new SimpleTextFigure(f.getConceptQuery(), getDescriptionString(f.getConceptQuery()));
        tf.setCoords(newX, newY);
        drawing.setLabelForConcept(concept, tf);

        Figure connected = makeConnectedFigure(f, tf);

        f.addDependend(connected);
        drawing.addForegroundFigure(connected);

        return connected;
    }

    protected void removeConnectedObjectFromContainer(conexp.frontend.latticeeditor.ConceptSetDrawing drawing, conexp.frontend.latticeeditor.figures.AbstractConceptCorrespondingFigure f, Object obj) {
        drawing.removeForegroundFigure((Figure) obj);
        f.removeDependend((Figure) obj);
    }

    protected void removeConnectedObjectFromContainer(java.util.Collection foreground, conexp.frontend.latticeeditor.figures.AbstractConceptCorrespondingFigure f, Object obj) {
        foreground.remove(obj);
        f.removeDependend((Figure) obj);
    }

    public void shutdown(ConceptSetDrawing drawing) {
        super.shutdown(drawing);
        drawing.clearConceptLabels();
    }
}
