package conexp.core.layout.layeredlayout;

import conexp.core.ConceptIterator;
import conexp.core.Lattice;
import conexp.core.LatticeElement;
import conexp.core.layout.PointUtilities;
import util.collection.CollectionFactory;
import util.gui.GraphicObjectsFactory;

import java.awt.geom.Point2D;
import java.util.Set;

/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 */
public class DifferentEdgeVectorsEvaluationFunction extends LatticeBasedEvaluationFunctionBase {

    //the bigger value of evaluation function is better
    public double getEvaluationForLattice() {
        final Set distinctVectors = CollectionFactory.createDefaultSet();
        lattice.forEach(new Lattice.LatticeElementVisitor() {
            Point2D nodeCoords = GraphicObjectsFactory.makePoint2D();
            Point2D childCoords = GraphicObjectsFactory.makePoint2D();

            public void visitNode(LatticeElement node) {
                conceptCoordinateMapper.setCoordsForConcept(node, nodeCoords);
                for (ConceptIterator successorIter = node.getSuccessors().iterator();
                     successorIter.hasNext();) {
                    LatticeElement child = successorIter.nextConcept();
                    conceptCoordinateMapper.setCoordsForConcept(child, childCoords);
                    Point2D vector = PointUtilities.normalizedEdgeVector(nodeCoords, childCoords);
                    distinctVectors.add(vector);
                }
            }
        });

        return -distinctVectors.size();
    }


}
