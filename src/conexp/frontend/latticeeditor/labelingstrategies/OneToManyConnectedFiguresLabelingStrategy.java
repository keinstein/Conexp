/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/

package conexp.frontend.latticeeditor.labelingstrategies;

import canvas.Figure;
import canvas.figures.TextFigure;
import conexp.core.ContextEntity;
import conexp.frontend.latticeeditor.ConceptSetDrawing;
import conexp.frontend.latticeeditor.DrawParameters;
import conexp.frontend.latticeeditor.figures.AbstractConceptCorrespondingFigure;
import conexp.frontend.latticeeditor.figures.ContextObjectTextFigure;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;


public abstract class OneToManyConnectedFiguresLabelingStrategy extends GenericLabelingStrategy {

    public OneToManyConnectedFiguresLabelingStrategy(DrawParameters opt) {
        super(opt);
    }

    protected void removeConnectedObjectFromContainer(ConceptSetDrawing drawing, AbstractConceptCorrespondingFigure f, java.lang.Object obj) {
        ArrayList arr = (ArrayList) obj;
        java.util.Iterator iter = arr.iterator();
        while (iter.hasNext()) {
            Figure curr = (Figure) iter.next();
            drawing.removeForegroundFigure(curr);
            f.removeDependend(curr);
        }
    }

    protected Object addObjectsFromIteratorToDrawingAccordingToDistributor(ConceptSetDrawing drawing,
                                                                           AbstractConceptCorrespondingFigure f,
                                                                           Iterator iter,
                                                                           int attrCount,
                                                                           PointDistributionStrategy distributor) {
        ArrayList objectLabels = new ArrayList(attrCount);

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

}
