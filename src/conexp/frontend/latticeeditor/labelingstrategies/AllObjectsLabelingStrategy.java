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

public class AllObjectsLabelingStrategy extends OneToManyConnectedFiguresLabelingStrategy {

    public AllObjectsLabelingStrategy(DrawParameters opt) {
        super(opt);
    }

    public boolean accept(ConceptQuery query) {
        return query.isInnermost() && query.hasOwnObjects();
    }

    protected java.lang.Object makeConnectedObject(ConceptSetDrawing drawing,
                                                   AbstractConceptCorrespondingFigure conceptFigure) {

        ConceptQuery conceptQuery = conceptFigure.getConceptQuery();
        int objCount = conceptQuery.getOwnObjectsCount();
        return addObjectsFromIteratorToDrawingAccordingToDistributor(drawing,
                conceptFigure,
                conceptQuery.ownObjectsIterator(),
                objCount,
                makeCoordsDistributor(conceptFigure, objCount));

    }

    protected PointDistributionStrategy makeCoordsDistributor(AbstractConceptCorrespondingFigure f, int numPoints) {
        return new LowerHemisphereUniformPointDistributionStrategy(f.getCenterX(),
                f.getCenterY(),
                numPoints,
                opt, new Ellipse2D.Double()
        );
    }

    public void shutdown(ConceptSetDrawing drawing) {
        super.shutdown(drawing);
        drawing.clearObjectLabels();
    }
}
