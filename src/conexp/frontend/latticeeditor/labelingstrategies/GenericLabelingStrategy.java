/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.latticeeditor.labelingstrategies;

import canvas.BaseFigureVisitor;
import canvas.Figure;
import canvas.figures.BorderCalculatingFigure;
import conexp.core.ExtendedContextEditingInterface;
import conexp.core.layout.LayoutParameters;
import conexp.frontend.latticeeditor.ConceptQuery;
import conexp.frontend.latticeeditor.ConceptSetDrawing;
import conexp.frontend.latticeeditor.LabelingStrategy;
import conexp.frontend.latticeeditor.figures.AbstractConceptCorrespondingFigure;
import conexp.frontend.latticeeditor.figures.CompositeFigureWithFigureDimensionCalcStrategyProvider;
import conexp.frontend.latticeeditor.figures.DefaultFigureVisitor;
import conexp.frontend.latticeeditor.figures.NodeObjectConnectionFigure;

import java.util.HashMap;
import java.util.Map;

public abstract class GenericLabelingStrategy extends LabelingStrategy {

    private Map/*<AbstractConceptCorrespondingFigure, Object>*/
            figureToConnectedObjectMap = new HashMap();


    public class InitStrategyVisitor extends DefaultFigureVisitor {
        ConceptSetDrawing drawing;
        LayoutParameters options;

        InitStrategyVisitor(ConceptSetDrawing aDrawing, LayoutParameters aOptions) {
            this.drawing = aDrawing;
            this.options = aOptions;
        }

        public void visitConceptCorrespondingFigure(AbstractConceptCorrespondingFigure figure) {
            if (!accept(figure.getConceptQuery())) {
                return;
            }
            setConnectedObject(figure, makeConnectedObject(drawing, figure, options));
        }
    }

    class ShutDownStrategyVisitor extends DefaultFigureVisitor {

        ConceptSetDrawing drawing;

        ShutDownStrategyVisitor(ConceptSetDrawing drawing) {
            this.drawing = drawing;
        }

        public void visitConceptCorrespondingFigure(AbstractConceptCorrespondingFigure figure) {
            if (!accept(figure.getConceptQuery())) {
                return;
            }
            Object connectedObject = getConnectedObject(figure);
            if (null == connectedObject) {
                return;
            }
            removeConnectedObjectFromContainer(drawing, figure, connectedObject);
            removeConnectedObject(figure);
        }
    }


    /**
     * GenericLabelingStrategy constructor comment.
     */

    protected GenericLabelingStrategy() {
        super();
    }


    public abstract boolean accept(ConceptQuery query);

    private void setConnectedObject(AbstractConceptCorrespondingFigure figure, Object object) {
        figureToConnectedObjectMap.put(figure, object);
    }

    /**
     * Insert the method's description here.
     * Creation date: (25.12.00 0:01:27)
     *
     * @param figure conexp.frontend.latticeeditor.Figures.ConceptFigure
     * @return java.lang.Object
     */
    private Object getConnectedObject(AbstractConceptCorrespondingFigure figure) {
        return figureToConnectedObjectMap.get(figure);
    }

    public boolean hasConnectedObjects() {
        return !figureToConnectedObjectMap.isEmpty();
    }

    protected abstract Object makeConnectedObject(ConceptSetDrawing drawing,
                                                  AbstractConceptCorrespondingFigure figure,
                                                  LayoutParameters options);


    private void removeConnectedObject(AbstractConceptCorrespondingFigure figure) {
        figureToConnectedObjectMap.remove(figure);
    }

    protected abstract void removeConnectedObjectFromContainer(ConceptSetDrawing drawing,
                                                               AbstractConceptCorrespondingFigure figure,
                                                               Object obj);


    protected abstract void clearLabels(ConceptSetDrawing drawing);

    public void setContext(ExtendedContextEditingInterface cxt) {
        //DEFAULT EMPTY IMPLEMENTATION
    }

    public BaseFigureVisitor makeInitStrategyVisitor(ConceptSetDrawing drawing, LayoutParameters opt) {
        return new InitStrategyVisitor(drawing, opt);
    }

    public BaseFigureVisitor makeShutDownVisitor(ConceptSetDrawing drawing) {
        return new ShutDownStrategyVisitor(drawing);
    }

    public void shutdown(ConceptSetDrawing drawing) {
        super.shutdown(drawing);
        clearLabels(drawing);
    }


    protected static Figure makeConnectedFigure(AbstractConceptCorrespondingFigure figure,
                                                BorderCalculatingFigure textFigure) {
        NodeObjectConnectionFigure connector = new NodeObjectConnectionFigure(figure, textFigure);
        CompositeFigureWithFigureDimensionCalcStrategyProvider compositeFigure =
                new CompositeFigureWithFigureDimensionCalcStrategyProvider();
        compositeFigure.addFigure(textFigure);
        compositeFigure.addFigure(connector);
        return compositeFigure;
    }

}
