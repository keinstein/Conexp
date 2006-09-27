/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.frontend.latticeeditor.labelingstrategies;

import conexp.frontend.latticeeditor.ConceptQuery;
import conexp.frontend.latticeeditor.figures.AbstractConceptCorrespondingFigure;

import java.util.Iterator;

import canvas.figures.BorderCalculatingFigure;


public class AllObjectsMultiLineLabelingStrategy extends MultiLineLabelingStrategyBase {
    public AllObjectsMultiLineLabelingStrategy() {
        super(DOWN_LABEL_LOCATION_STRATEGY);
    }

    protected BorderCalculatingFigure makeLabelForConceptCorrespondingFigure(AbstractConceptCorrespondingFigure f) {
        Iterator iterator = f.getConcept().ownObjectsIterator();
        return buildMultiLineFigureFromEntityIterator(iterator, f.getConceptQuery(), true);
    }

    public boolean accept(ConceptQuery query) {
        return query.hasOwnObjects();
    }
}
