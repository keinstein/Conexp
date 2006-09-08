/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.frontend.latticeeditor;

import conexp.core.Concept;
import conexp.core.ConceptsCollection;
import conexp.frontend.latticeeditor.figures.AbstractConceptCorrespondingFigure;
import conexp.frontend.latticeeditor.figures.Collidable;
import conexp.frontend.latticeeditor.figures.EdgeFigure;
import util.collection.CollectionFactory;
import util.comparators.ComparatorUtil;

import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;



public class CollisionDetector {

    public static void detectCollisions(LatticeDrawing conceptSetDrawing) {
        List conceptFigures = getNodeListOrderedByYCoord(conceptSetDrawing);
        clearCollisions(conceptFigures);
        if (conceptSetDrawing.isCollisionDetectionEnabled()) {
            DrawParamsProvider drawingSchema = conceptSetDrawing.getLatticeDrawingSchema();
            final int cutoffDistance = 2 * drawingSchema.getDrawParams().getMaxNodeRadius();
            detectInternodeCollision(conceptFigures, cutoffDistance);
        }
        List edgeFigures = conceptSetDrawing.getEdges();
        clearCollisions(edgeFigures);
        if (conceptSetDrawing.isCollisionDetectionEnabled()) {
            detectNodeEdgeCollisions(edgeFigures, conceptFigures);
        }
    }

    private static void detectNodeEdgeCollisions(List edgeFigures, List conceptFigures) {
        final int edgeCount = edgeFigures.size();
        Rectangle edgeRect = new Rectangle();
        Rectangle nodeRect = new Rectangle();
        for (int i = 0; i < edgeCount; i++) {
            EdgeFigure edgeFigure = (EdgeFigure) edgeFigures.get(i);
            edgeFigure.boundingBox(edgeRect);
            Line2D line = edgeFigure.getLine();
            for (int nodeIndex = 0; nodeIndex < conceptFigures.size(); nodeIndex++) {
                AbstractConceptCorrespondingFigure nodeFigure = (AbstractConceptCorrespondingFigure) conceptFigures.get(nodeIndex);
                nodeFigure.boundingBox(nodeRect);
                nodeRect.grow(2, 2);
                if (edgeRect.getMaxY() < nodeRect.getMinY()) {
                    break;
                }
                if (!nodeRect.intersects(edgeRect)) {
                    continue;
                }
                if (edgeFigure.containsFigure(nodeFigure)) {
                    continue;
                }
                if (line.intersects(nodeRect)) {
                    edgeFigure.setCollision(true);
                    nodeFigure.setCollision(true);
                }
            }
        }
    }

    private static void detectInternodeCollision(List conceptFigures, final int cutoffDistance) {
        final int bound = conceptFigures.size();
        for (int i = 0; i < bound; i++) {
            AbstractConceptCorrespondingFigure currentFigure = (AbstractConceptCorrespondingFigure) conceptFigures.get(i);
            for (int j = i + 1; j < bound; j++) {
                AbstractConceptCorrespondingFigure otherFigure = (AbstractConceptCorrespondingFigure) conceptFigures.get(j);
                final double verticalDistance = Math.abs(otherFigure.getCenterY() - currentFigure.getCenterY());
                if (verticalDistance > cutoffDistance) {
                    break;
                }
                if (hasIntersection(currentFigure, otherFigure)) {
                    currentFigure.setCollision(true);
                    otherFigure.setCollision(true);
                }
            }
        }
    }

    public static void clearCollisions(List conceptFigures) {
        for (Iterator iterator = conceptFigures.iterator(); iterator.hasNext();) {
            Collidable figure = (Collidable) iterator.next();
            figure.setCollision(false);
        }
    }

    private static List getNodeListOrderedByYCoord(final ConceptSetDrawing conceptSetDrawing) {
        final List conceptFigures = CollectionFactory.createDefaultList();
        conceptSetDrawing.getConceptSet().forEach(new ConceptsCollection.ConceptVisitor() {
            public void visitConcept(Concept c) {
                AbstractConceptCorrespondingFigure figure = conceptSetDrawing.getFigureForConcept(c);
                conceptFigures.add(figure);
            }
        });
        Collections.sort(conceptFigures, new Comparator() {
            public int compare(Object o1, Object o2) {
                AbstractConceptCorrespondingFigure f1 = (AbstractConceptCorrespondingFigure) o1;
                AbstractConceptCorrespondingFigure f2 = (AbstractConceptCorrespondingFigure) o2;
                return ComparatorUtil.compareDoubles(f1.getCenterY(), f2.getCenterY());
            }
        });
        return conceptFigures;
    }

    private static boolean hasIntersection(AbstractConceptCorrespondingFigure currentFigure, AbstractConceptCorrespondingFigure otherFigure) {
        Rectangle currentFigureBounds = new Rectangle();
        Rectangle otherFigureBounds = new Rectangle();
        currentFigure.boundingBox(currentFigureBounds);
        otherFigure.boundingBox(otherFigureBounds);
        if (!currentFigureBounds.intersects(otherFigureBounds)) {
            return false;
        }
        //todo: maybe implement more advanced strategy;
        return true;
    }
}
