/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.frontend.latticeeditor.labelingstrategies;

import canvas.CanvasScheme;
import canvas.figures.BorderCalculatingFigure;
import canvas.figures.ColorTransformerWithFadeOut;
import canvas.figures.MultiLineTextFigure;
import conexp.core.ContextEntity;
import conexp.core.Set;
import conexp.core.layout.LayoutParameters;
import conexp.frontend.latticeeditor.ConceptQuery;
import conexp.frontend.latticeeditor.figures.IConceptRelatedTextFigure;
import conexp.frontend.latticeeditor.figures.ColorUtil;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;



public abstract class MultiLineLabelingStrategyBase extends GenericConceptLabelingStrategy {
    private static final FontRenderContext DEFAULT_FONT_RENDER_CONTEXT = new FontRenderContext(null, true, false);

    protected MultiLineLabelingStrategyBase(LabelLocationStrategy strategy) {
        super(strategy);
    }

    protected double calculateVerticalOffset(LayoutParameters options, Rectangle2D rect, double angle) {
        return (1.5 * options.getMaxNodeRadius() + rect.getHeight() / 2) * Math.sin(angle);
    }

    protected static BorderCalculatingFigure buildMultiLineFigureFromEntityIterator(Iterator iterator, final ConceptQuery conceptQuery, boolean isObject) {
        MultiLineConceptEntityFigure figure = new MultiLineConceptEntityFigure(conceptQuery, isObject);
        figure.setText(buildNamesStringFromEntityIterator(iterator));
        figure.newSize(DEFAULT_FONT_RENDER_CONTEXT);
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

    //todo:sye - change to protected
    public static class MultiLineConceptEntityFigure extends MultiLineTextFigure implements IConceptRelatedTextFigure {
        private final ConceptQuery conceptQuery;
        private final boolean isObject;

        protected Color getBackground(CanvasScheme opt) {
            return ColorUtil.getLabelBackgroungColor(isObject);
        }

        public MultiLineConceptEntityFigure(ConceptQuery newConceptQuery, boolean newIsObject) {
            setColorTransformer(ColorTransformerWithFadeOut.getInstance());
            this.conceptQuery = newConceptQuery;
            this.isObject = newIsObject;
        }

        public Set getIntentQuery() {
            return conceptQuery.getQueryIntent();
        }

        protected String getContentDescription() {
            Rectangle2D rect = new Rectangle();
            boundingBox(rect);
            return super.getContentDescription() + " bounding box=" + rect;
        }
    }
}
