/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.frontend.latticeeditor.labelingstrategies;

import canvas.figures.BorderCalculatingFigure;
import conexp.core.layout.LayoutParameters;
import conexp.frontend.latticeeditor.ConceptQuery;
import conexp.frontend.latticeeditor.figures.AbstractConceptCorrespondingFigure;
import conexp.frontend.latticeeditor.figures.SimpleTextFigure;

import java.awt.geom.Rectangle2D;



public abstract class OneLabelConceptLabelingStrategy extends GenericConceptLabelingStrategy {
    protected OneLabelConceptLabelingStrategy() {
        super();
    }

    protected OneLabelConceptLabelingStrategy(LabelLocationStrategy labelLocationStrategy) {
        super(labelLocationStrategy);
    }

    protected double calculateVerticalOffset(LayoutParameters options, Rectangle2D rect, double angle) {
        return options.getGridSizeY() / 3 * Math.sin(angle);
    }

    protected BorderCalculatingFigure makeLabelForConceptCorrespondingFigure(AbstractConceptCorrespondingFigure figure) {
        return new SimpleTextFigure(figure.getConceptQuery(),
                getDescriptionString(figure.getConceptQuery()),
                labelLocationStrategy==DOWN_LABEL_LOCATION_STRATEGY);
    }


    protected abstract String getDescriptionString(ConceptQuery conceptQuery);
}
