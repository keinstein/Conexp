package conexp.frontend.latticeeditor.labelingstrategies;

import canvas.figures.BorderCalculatingFigure;
import canvas.Figure;
import conexp.core.ExtendedContextEditingInterface;
import conexp.core.LatticeElement;
import conexp.core.layout.LayoutParameters;
import conexp.frontend.latticeeditor.ConceptSetDrawing;
import conexp.frontend.latticeeditor.figures.AbstractConceptCorrespondingFigure;

import java.awt.geom.Rectangle2D;

import util.gui.GraphicObjectsFactory;

public abstract class GenericConceptLabelingStrategy extends GenericLabelingStrategy {
    protected ExtendedContextEditingInterface cxt;

    public void setContext(ExtendedContextEditingInterface newCxt) {
        this.cxt = newCxt;
    }

    protected abstract double calculateVerticalOffset(LayoutParameters options, Rectangle2D rect, double angle);

    protected Object makeConnectedObject(ConceptSetDrawing drawing,
                                         AbstractConceptCorrespondingFigure figure,
                                         LayoutParameters options) {
        LatticeElement concept = figure.getConcept();
        BorderCalculatingFigure labelFigure = makeLabelForConceptCorrespondingFigure(figure);
        Rectangle2D rect = GraphicObjectsFactory.makeRectangle2D();
        labelFigure.boundingBox(rect);

        double angle = getLabelLocationAngleInRadians();
        double horisontalOffset = options.getGridSizeX() / 2 * Math.cos(angle);
        double verticalOffset = calculateVerticalOffset(options, rect, angle);

        int newX = (int) (figure.getCenterX() + horisontalOffset);
        int newY = (int) (figure.getCenterY() + verticalOffset);

        labelFigure.setCoords(newX, newY);
        setLabelForConcept(drawing, concept, labelFigure);

        Figure connected = makeConnectedFigure(figure, labelFigure);

        figure.addDependend(connected);
        drawing.addForegroundFigure(connected);

        return connected;
    }

    protected abstract BorderCalculatingFigure makeLabelForConceptCorrespondingFigure(AbstractConceptCorrespondingFigure f);

    protected void removeConnectedObjectFromContainer(ConceptSetDrawing drawing, AbstractConceptCorrespondingFigure
            conceptFigure, Object obj) {
        drawing.removeForegroundFigure((Figure) obj);
        conceptFigure.removeDependend((Figure) obj);
    }


    interface LabelLocationStrategy {
        void setLabelForConcept(ConceptSetDrawing drawing,
                                LatticeElement concept,
                                BorderCalculatingFigure labelFigure);

        void clearLabels(ConceptSetDrawing drawing);

        double getLabelLocationAngleInRadians();
    }

    static class DownLabelLocationStrategy implements LabelLocationStrategy {
        public void setLabelForConcept(ConceptSetDrawing drawing, LatticeElement concept, BorderCalculatingFigure labelFigure) {
            drawing.setDownLabelForConcept(concept, labelFigure);
        }

        public void clearLabels(ConceptSetDrawing drawing) {
            drawing.clearDownLabelsForConcepts();
        }

        public double getLabelLocationAngleInRadians() {
            return 0.5 * Math.PI;
        }
    }

    static class UpLabelLocationStrategy implements LabelLocationStrategy {
        public void setLabelForConcept(ConceptSetDrawing drawing, LatticeElement concept, BorderCalculatingFigure labelFigure) {
            drawing.setUpLabelForConcept(concept, labelFigure);
        }

        public void clearLabels(ConceptSetDrawing drawing) {
            drawing.clearUpLabelsForConcepts();
        }

        public double getLabelLocationAngleInRadians() {
            return 1.5* Math.PI;
        }
    }

    static final LabelLocationStrategy UP_LABEL_LOCATION_STRATEGY = new UpLabelLocationStrategy();
    static final LabelLocationStrategy DOWN_LABEL_LOCATION_STRATEGY = new DownLabelLocationStrategy();

    LabelLocationStrategy labelLocationStrategy;

    protected GenericConceptLabelingStrategy(LabelLocationStrategy labelLocationStrategy) {
        this.labelLocationStrategy = labelLocationStrategy;
    }

    protected GenericConceptLabelingStrategy() {
        this(DOWN_LABEL_LOCATION_STRATEGY);
    }

    protected void setLabelForConcept(ConceptSetDrawing drawing,
                                      LatticeElement concept,
                                      BorderCalculatingFigure labelFigure) {
        labelLocationStrategy.setLabelForConcept(drawing, concept, labelFigure);
    }

    protected void clearLabels(ConceptSetDrawing drawing) {
        labelLocationStrategy.clearLabels(drawing);
    }

    //todo: sye - change to protected after source structure change
    public double getLabelLocationAngleInRadians(){
        return labelLocationStrategy.getLabelLocationAngleInRadians();
    }

}
