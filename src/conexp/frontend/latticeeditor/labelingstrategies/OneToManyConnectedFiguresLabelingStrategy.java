/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.latticeeditor.labelingstrategies;

import canvas.Figure;
import canvas.figures.TextFigure;
import conexp.core.ContextEntity;
import conexp.core.layout.LayoutParameters;
import conexp.frontend.latticeeditor.ConceptSetDrawing;
import conexp.frontend.latticeeditor.figures.AbstractConceptCorrespondingFigure;
import conexp.frontend.latticeeditor.figures.ContextEntityTextFigure;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public abstract class OneToManyConnectedFiguresLabelingStrategy extends GenericLabelingStrategy {

    protected OneToManyConnectedFiguresLabelingStrategy() {
        super();
    }

    protected void removeConnectedObjectFromContainer(ConceptSetDrawing drawing,
                                                      AbstractConceptCorrespondingFigure figure, Object obj) {
        List list = (List) obj;
        Iterator iter = list.iterator();
        while (iter.hasNext()) {
            Figure current = (Figure) iter.next();
            drawing.removeForegroundFigure(current);
            figure.removeDependend(current);
        }
    }

    protected static Object addObjectsFromIteratorToDrawingAccordingToDistributor(ConceptSetDrawing drawing,
                                                         AbstractConceptCorrespondingFigure figure,
                                                         Iterator iter,
                                                         int attrCount,
                                                         PointDistributionStrategy distributor) {
        List objectLabels = new ArrayList(attrCount);

        Point2D coords = new Point2D.Double();

        while (iter.hasNext()) {
            distributor.setNextCoords(coords);
            ContextEntity object = (ContextEntity) iter.next();
            TextFigure textFigure = new ContextEntityTextFigure(figure.getConceptQuery(), object);
            textFigure.setCoords(coords);
            drawing.setFigureForContextObject(object, textFigure);

            Figure connectedFigure = makeConnectedFigure(figure, textFigure);
            figure.addDependend(connectedFigure);
            drawing.addForegroundFigure(connectedFigure);
            objectLabels.add(connectedFigure);
        }

        return objectLabels;
    }

    protected abstract PointDistributionStrategy makeCoordsDistributor(AbstractConceptCorrespondingFigure figure,
                                                                       int numPoints, LayoutParameters opt);

}
