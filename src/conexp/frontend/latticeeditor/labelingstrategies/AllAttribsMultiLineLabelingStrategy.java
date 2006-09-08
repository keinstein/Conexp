package conexp.frontend.latticeeditor.labelingstrategies;

import canvas.figures.BorderCalculatingFigure;
import conexp.core.LatticeElement;
import conexp.frontend.latticeeditor.ConceptQuery;
import conexp.frontend.latticeeditor.ConceptSetDrawing;
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

    public double getLabelLocationAngleInRadians() {
        return 1.5 * Math.PI;
    }

    public void setLabelForConcept(ConceptSetDrawing drawing,
                                   LatticeElement concept,
                                   BorderCalculatingFigure labelFigure) {
        drawing.setUpLabelForConcept(concept, labelFigure);
    }

    public void shutdown(ConceptSetDrawing drawing) {
        super.shutdown(drawing);
        drawing.clearUpLabelsForConcepts();
    }

    protected MultiLineConceptEntityFigure makeLabelForConceptCorrespondingFigure(AbstractConceptCorrespondingFigure f) {
        return buildMultiLineFigureFromEntityIterator(f.getConcept().ownAttribsIterator(), f.getConceptQuery(), false);
    }

    public boolean accept(ConceptQuery query) {
        return query.hasOwnAttribs();
    }
}
