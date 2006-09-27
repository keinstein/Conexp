/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.frontend.latticeeditor.labelingstrategies;

import conexp.frontend.latticeeditor.ConceptQuery;
import conexp.frontend.latticeeditor.figures.AbstractConceptCorrespondingFigure;
import canvas.figures.BorderCalculatingFigure;


public class AllAttribsMultiLineLabelingStrategy extends MultiLineLabelingStrategyBase {
    public AllAttribsMultiLineLabelingStrategy() {
        super(UP_LABEL_LOCATION_STRATEGY);
    }

    public boolean accept(ConceptQuery query) {
        return query.hasOwnAttribs();
    }

    protected BorderCalculatingFigure makeLabelForConceptCorrespondingFigure(AbstractConceptCorrespondingFigure f) {
        return buildMultiLineFigureFromEntityIterator(f.getConcept().ownAttribsIterator(), f.getConceptQuery(), false);
    }

}
