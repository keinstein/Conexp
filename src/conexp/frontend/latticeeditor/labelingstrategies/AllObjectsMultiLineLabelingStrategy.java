package conexp.frontend.latticeeditor.labelingstrategies;

import canvas.figures.BorderCalculatingFigure;
import conexp.frontend.latticeeditor.ConceptQuery;
import conexp.frontend.latticeeditor.figures.AbstractConceptCorrespondingFigure;

import java.util.Iterator;

/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 * Date: 30/7/2003
 * Time: 22:19:52
 */

public class AllObjectsMultiLineLabelingStrategy extends MultiLineLabelingStrategyBase {
    public AllObjectsMultiLineLabelingStrategy() {
        super();
    }

    protected BorderCalculatingFigure makeLabelForConceptCorrespondingFigure(AbstractConceptCorrespondingFigure f) {
        Iterator iterator = f.getConcept().ownObjectsIterator();
        return buildMultiLineFigureFromEntityIterator(iterator);
    }

    public boolean accept(ConceptQuery query) {
        return query.hasOwnObjects();
    }
}
