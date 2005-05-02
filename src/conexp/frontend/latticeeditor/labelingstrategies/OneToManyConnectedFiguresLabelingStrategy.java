/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
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
import conexp.frontend.latticeeditor.figures.ContextObjectTextFigure;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public abstract class OneToManyConnectedFiguresLabelingStrategy extends GenericLabelingStrategy {

    protected OneToManyConnectedFiguresLabelingStrategy() {
        super();
    }

    protected void removeConnectedObjectFromContainer(ConceptSetDrawing drawing, AbstractConceptCorrespondingFigure f, Object obj) {
        List arr = (List) obj;
        Iterator iter = arr.iterator();
        while (iter.hasNext()) {
            Figure curr = (Figure) iter.next();
            drawing.removeForegroundFigure(curr);
            f.removeDependend(curr);
        }
    }

    protected static Object addObjectsFromIteratorToDrawingAccordingToDistributor(ConceptSetDrawing drawing,
                                                                                  AbstractConceptCorrespondingFigure f,
                                                                                  Iterator iter,
                                                                                  int attrCount,
                                                                                  PointDistributionStrategy distributor) {
        List objectLabels = new ArrayList(attrCount);

        Point2D coords = new Point2D.Double();

        while (iter.hasNext()) {
            distributor.setNextCoords(coords);
            ContextEntity object = (ContextEntity) iter.next();
            TextFigure tf = new ContextObjectTextFigure(f.getConceptQuery(), object);
            tf.setCoords(coords);
            drawing.setFigureForContextObject(object, tf);

            Figure cf = makeConnectedFigure(f, tf);
            f.addDependend(cf);
            drawing.addForegroundFigure(cf);
            objectLabels.add(cf);
        }

        return objectLabels;
    }

    protected abstract PointDistributionStrategy makeCoordsDistributor(AbstractConceptCorrespondingFigure f, int numPoints, LayoutParameters opt);

}
