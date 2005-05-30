package conexp.frontend.latticeeditor.labelingstrategies;

import canvas.figures.BorderCalculatingFigure;
import conexp.frontend.latticeeditor.ConceptQuery;
import conexp.frontend.latticeeditor.figures.AbstractConceptCorrespondingFigure;

/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 * Date: 30/7/2003
 * Time: 22:19:52
 */

public class AllAttribsMultiLineLabelingStrategy extends MultiLineLabelingStrategyBase {
    public AllAttribsMultiLineLabelingStrategy() {
        super();
    }

    boolean isUpper() {
        return true;
    }

    protected MultiLineConceptEntityFigure makeLabelForConceptCorrespondingFigure(AbstractConceptCorrespondingFigure f) {
        return buildMultiLineFigureFromEntityIterator(f.getConcept().ownAttribsIterator(), f.getConceptQuery(), false);
    }

    public boolean accept(ConceptQuery query) {
        return query.hasOwnAttribs();
    }
}
