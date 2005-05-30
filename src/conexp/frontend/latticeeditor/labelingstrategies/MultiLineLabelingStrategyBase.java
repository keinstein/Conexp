package conexp.frontend.latticeeditor.labelingstrategies;

import canvas.CanvasScheme;
import canvas.Figure;
import canvas.figures.ColorTransformerWithFadeOut;
import canvas.figures.MultiLineTextFigure;
import conexp.core.ContextEntity;
import conexp.core.ExtendedContextEditingInterface;
import conexp.core.LatticeElement;
import conexp.core.Set;
import conexp.core.layout.LayoutParameters;
import conexp.frontend.latticeeditor.ConceptQuery;
import conexp.frontend.latticeeditor.ConceptSetDrawing;
import conexp.frontend.latticeeditor.figures.AbstractConceptCorrespondingFigure;
import conexp.frontend.latticeeditor.figures.IConceptRelatedTextFigure;
import util.gui.GraphicObjectsFactory;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.util.Collection;
import java.util.Iterator;

/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 * Date: 30/7/2003
 * Time: 22:55:43
 */

public abstract class MultiLineLabelingStrategyBase extends GenericLabelingStrategy {
    protected ExtendedContextEditingInterface cxt;
    private static final FontRenderContext DEFAULT_FRC = new FontRenderContext(null, true, false);

    protected MultiLineLabelingStrategyBase() {
        super();
    }

    public void setContext(ExtendedContextEditingInterface cxt) {
        this.cxt = cxt;
    }

    abstract boolean isUpper();

    protected Object makeConnectedObject(ConceptSetDrawing drawing, AbstractConceptCorrespondingFigure f, LayoutParameters opt) {
        LatticeElement concept = f.getConcept();
        MultiLineConceptEntityFigure labelFigure = makeLabelForConceptCorrespondingFigure(f);
        labelFigure.newSize(DEFAULT_FRC);
        Rectangle2D rect = GraphicObjectsFactory.makeRectangle2D();
        labelFigure.boundingBox(rect);
        double angle = (isUpper()? 1.5 :0.5 ) * Math.PI;
        double offX = opt.getGridSizeX() / 2 * Math.cos(angle);

        double offY = (1.5 * opt.getMaxNodeRadius()+rect.getHeight()/2) * Math.sin(angle);
        int newX = (int) (f.getCenterX() + offX);
        int newY = (int) (f.getCenterY() + offY);
        labelFigure.setCoords(newX, newY);
        drawing.setLabelForConcept(concept, labelFigure);

        Figure connected = makeConnectedFigure(f, labelFigure);

        f.addDependend(connected);
        drawing.addForegroundFigure(connected);

        return connected;
    }

    protected abstract MultiLineConceptEntityFigure makeLabelForConceptCorrespondingFigure(AbstractConceptCorrespondingFigure f);

    protected void removeConnectedObjectFromContainer(ConceptSetDrawing drawing, AbstractConceptCorrespondingFigure f, Object obj) {
        drawing.removeForegroundFigure((Figure) obj);
        f.removeDependend((Figure) obj);
    }

    protected static void removeConnectedObjectFromContainer(Collection foreground, AbstractConceptCorrespondingFigure f, Object obj) {
        foreground.remove(obj);
        f.removeDependend((Figure) obj);
    }

    public void shutdown(ConceptSetDrawing drawing) {
        super.shutdown(drawing);
        drawing.clearConceptLabels();
    }

    protected static MultiLineConceptEntityFigure buildMultiLineFigureFromEntityIterator(Iterator iterator, final ConceptQuery conceptQuery, boolean isObject) {
        MultiLineConceptEntityFigure figure = new MultiLineConceptEntityFigure(conceptQuery, isObject);
        figure.setText(buildNamesStringFromEntityIterator(iterator));
        return figure;
    }

    private static String buildNamesStringFromEntityIterator(Iterator iterator) {
        StringBuffer names = new StringBuffer();
        boolean first = true;
        for (; iterator.hasNext();) {
            if (first) {
                first = false;
            } else {
                names.append('\n');
            }
            names.append(((ContextEntity) iterator.next()).getName());
        }
        return names.toString();
    }

    protected static class MultiLineConceptEntityFigure extends MultiLineTextFigure implements IConceptRelatedTextFigure{
        private final ConceptQuery conceptQuery;
        private final boolean isObject;

        protected Color getBackground(CanvasScheme opt) {
            return isObject ? Color.white : Color.lightGray;
        }

        public MultiLineConceptEntityFigure(ConceptQuery conceptQuery, boolean isObject) {
            setColorTransformer(ColorTransformerWithFadeOut.getInstance());
            this.conceptQuery = conceptQuery;
            this.isObject = isObject;
        }

        public Set getIntentQuery() {
            return conceptQuery.getQueryIntent();
        }
    }
}
