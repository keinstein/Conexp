package conexp.frontend.latticeeditor.labelingstrategies;

import canvas.figures.BorderCalculatingFigure;
import conexp.core.LatticeElement;
import conexp.frontend.latticeeditor.ConceptQuery;
import conexp.frontend.latticeeditor.ConceptSetDrawing;
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

    //todo:sye - change to package local
    public double getLabelLocationAngleInRadians() {
        return 0.5 * Math.PI;
    }

    protected MultiLineConceptEntityFigure makeLabelForConceptCorrespondingFigure(AbstractConceptCorrespondingFigure f) {
        Iterator iterator = f.getConcept().ownObjectsIterator();
        return buildMultiLineFigureFromEntityIterator(iterator, f.getConceptQuery(), true);
    }

    public boolean accept(ConceptQuery query) {
        return query.hasOwnObjects();
    }

    public void setLabelForConcept(ConceptSetDrawing drawing,
                                   LatticeElement concept,
                                   BorderCalculatingFigure labelFigure) {
        drawing.setDownLabelForConcept(concept, labelFigure);
    }

    public void shutdown(ConceptSetDrawing drawing) {
        drawing.clearDownLabelsForConcepts();
        super.shutdown(drawing);
    }
}
