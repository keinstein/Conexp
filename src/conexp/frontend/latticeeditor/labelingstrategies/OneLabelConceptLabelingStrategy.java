package conexp.frontend.latticeeditor.labelingstrategies;

import canvas.Figure;
import canvas.figures.BorderCalculatingFigure;
import conexp.core.LatticeElement;
import conexp.frontend.latticeeditor.ConceptSetDrawing;
import conexp.frontend.latticeeditor.DrawParameters;
import conexp.frontend.latticeeditor.figures.AbstractConceptCorrespondingFigure;

/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 * Date: 30/7/2003
 * Time: 22:55:43
 */

public abstract class OneLabelConceptLabelingStrategy extends GenericLabelingStrategy {
    protected conexp.core.ExtendedContextEditingInterface cxt;

    public OneLabelConceptLabelingStrategy() {
        super();
    }

    public void setContext(conexp.core.ExtendedContextEditingInterface cxt) {
        this.cxt = cxt;
    }

    protected Object makeConnectedObject(ConceptSetDrawing drawing, AbstractConceptCorrespondingFigure f, DrawParameters opt) {
        double angle = 0.5 * Math.PI;
        double offX = opt.getGridSizeX() / 2 * Math.cos(angle);
        double offY = opt.getGridSizeY() / 3 * Math.sin(angle);
        int newX = (int) (f.getCenterX() + offX);
        int newY = (int) (f.getCenterY() + offY);

        LatticeElement concept = f.getConcept();
        BorderCalculatingFigure labelFigure = makeLabelForConceptCorrespondingFigure(f);
        labelFigure.setCoords(newX, newY);
        drawing.setLabelForConcept(concept, labelFigure);

        Figure connected = makeConnectedFigure(f, labelFigure);

        f.addDependend(connected);
        drawing.addForegroundFigure(connected);

        return connected;
    }

    protected abstract BorderCalculatingFigure makeLabelForConceptCorrespondingFigure(AbstractConceptCorrespondingFigure f);

    protected void removeConnectedObjectFromContainer(ConceptSetDrawing drawing, AbstractConceptCorrespondingFigure f, Object obj) {
        drawing.removeForegroundFigure((Figure) obj);
        f.removeDependend((Figure) obj);
    }

    protected void removeConnectedObjectFromContainer(java.util.Collection foreground, AbstractConceptCorrespondingFigure f, Object obj) {
        foreground.remove(obj);
        f.removeDependend((Figure) obj);
    }

    public void shutdown(ConceptSetDrawing drawing) {
        super.shutdown(drawing);
        drawing.clearConceptLabels();
    }
}
