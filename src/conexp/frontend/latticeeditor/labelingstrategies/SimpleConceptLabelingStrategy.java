/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor.labelingstrategies;

import canvas.figures.BorderCalculatingFigure;
import conexp.frontend.latticeeditor.ConceptQuery;
import conexp.frontend.latticeeditor.figures.AbstractConceptCorrespondingFigure;
import conexp.frontend.latticeeditor.figures.SimpleTextFigure;


public abstract class SimpleConceptLabelingStrategy extends OneLabelConceptLabelingStrategy {

    protected SimpleConceptLabelingStrategy() {
        super();
    }

    protected abstract String getDescriptionString(ConceptQuery conceptQuery);

    protected BorderCalculatingFigure makeLabelForConceptCorrespondingFigure(AbstractConceptCorrespondingFigure f) {
        return new SimpleTextFigure(f.getConceptQuery(), getDescriptionString(f.getConceptQuery()));
    }

}
