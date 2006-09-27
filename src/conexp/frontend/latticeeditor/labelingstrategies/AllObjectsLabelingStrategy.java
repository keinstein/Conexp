/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor.labelingstrategies;

import conexp.core.layout.LayoutParameters;
import conexp.frontend.latticeeditor.ConceptQuery;
import conexp.frontend.latticeeditor.ConceptSetDrawing;
import conexp.frontend.latticeeditor.figures.AbstractConceptCorrespondingFigure;

import java.awt.geom.Ellipse2D;

public class AllObjectsLabelingStrategy extends OneToManyConnectedFiguresLabelingStrategy {

    public AllObjectsLabelingStrategy() {
        super();
    }

    public boolean accept(ConceptQuery query) {
        return query.isInnermost() && query.hasOwnObjects();
    }

    protected Object makeConnectedObject(ConceptSetDrawing drawing,
                                         AbstractConceptCorrespondingFigure conceptFigure, LayoutParameters opt) {

        ConceptQuery conceptQuery = conceptFigure.getConceptQuery();
        int objCount = conceptQuery.getOwnObjectsCount();
        return addObjectsFromIteratorToDrawingAccordingToDistributor(drawing,
                conceptFigure,
                conceptQuery.ownObjectsIterator(),
                objCount,
                makeCoordsDistributor(conceptFigure, objCount, opt));

    }

    protected PointDistributionStrategy makeCoordsDistributor(AbstractConceptCorrespondingFigure f, int numPoints, LayoutParameters opt) {
        return new LowerHemisphereUniformPointDistributionStrategy(f.getCenterX(),
                f.getCenterY(),
                numPoints,
                opt, new Ellipse2D.Double());
    }

    protected void clearLabels(ConceptSetDrawing drawing) {
        drawing.clearObjectLabels();
    }
}
