/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.latticeeditor.labelingstrategies;

import conexp.frontend.latticeeditor.ConceptQuery;
import conexp.frontend.latticeeditor.ConceptSetDrawing;
import conexp.frontend.latticeeditor.DrawParameters;
import conexp.frontend.latticeeditor.figures.AbstractConceptCorrespondingFigure;

import java.awt.geom.Ellipse2D;

public class AllAttribsLabelingStrategy extends OneToManyConnectedFiguresLabelingStrategy {

    public AllAttribsLabelingStrategy() {
        super();
    }

    public boolean accept(ConceptQuery query) {
        return query.hasOwnAttribs();
    }

    protected java.lang.Object makeConnectedObject(ConceptSetDrawing drawing, AbstractConceptCorrespondingFigure conceptFigure, DrawParameters opt) {
        ConceptQuery conceptQuery = conceptFigure.getConceptQuery();
        int attrCount = conceptQuery.getOwnAttribsCount();

        return addObjectsFromIteratorToDrawingAccordingToDistributor(drawing,
                conceptFigure,
                conceptQuery.ownAttribsIterator(),
                attrCount,
                makeCoordsDistributor(conceptFigure, attrCount, opt));

    }

    protected PointDistributionStrategy makeCoordsDistributor(AbstractConceptCorrespondingFigure f, int numPoints, DrawParameters opt) {
        return new UpperHemisphereUniformPointDistributionStrategy(f.getCenterX(),
                f.getCenterY(),
                numPoints,
                opt, new Ellipse2D.Double());
    }

    public void shutdown(ConceptSetDrawing drawing) {
        super.shutdown(drawing);
        drawing.clearAttributeLabels();
    }
}
