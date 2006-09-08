/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.core.layout.layeredlayout;

import conexp.core.Lattice;
import conexp.core.LatticeElement;
import conexp.core.LatticeElementCollection;
import conexp.core.layout.ConceptCoordinateMapper;
import conexp.core.layout.PointUtilities;
import util.gui.GraphicObjectsFactory;

import java.awt.geom.Point2D;



public class ThreeElementsChainCountEvaluationFunction extends LatticeBasedEvaluationFunctionBase {
    public ThreeElementsChainCountEvaluationFunction() {
    }

    public ThreeElementsChainCountEvaluationFunction(Lattice lattice, ConceptCoordinateMapper conceptCoordinateMapper) {
        super(lattice, conceptCoordinateMapper);
    }

    public double getEvaluationForLattice() {
        ThreeElementsChainCounterLatticeElementVisitor visitor = new ThreeElementsChainCounterLatticeElementVisitor();
        lattice.forEach(visitor);
        return visitor.getChainCount();
    }

    private class ThreeElementsChainCounterLatticeElementVisitor implements Lattice.LatticeElementVisitor {
        int chainCount = 0;

        public int getChainCount() {
            return chainCount;
        }

        Point2D parentCoord = GraphicObjectsFactory.makePoint2D();
        Point2D nodeCoord = GraphicObjectsFactory.makePoint2D();
        Point2D childCoord = GraphicObjectsFactory.makePoint2D();

        public void visitNode(LatticeElement node) {
            conceptCoordinateMapper.setCoordsForConcept(node, nodeCoord);
            LatticeElementCollection parents = node.getPredecessors();
            Point2D[] parentVectors = generateParentVectors(parents);
            Point2D[] childVectors = generateChildVectors(node);
            for (int i = 0; i < parentVectors.length; i++) {
                Point2D parentVector = parentVectors[i];
                for (int j = 0; j < childVectors.length; j++) {
                    if (parentVector.equals(childVectors[j])) {
                        chainCount++;
                    }
                }
            }
        }

        private Point2D[] generateChildVectors(LatticeElement node) {
            LatticeElementCollection children = node.getSuccessors();
            int childCount = children.getSize();
            Point2D[] childVectors = new Point2D[childCount];
            for (int childIndex = 0; childIndex < childCount; childIndex++) {
                LatticeElement child = children.get(childIndex);
                conceptCoordinateMapper.setCoordsForConcept(child, childCoord);
                childVectors[childIndex] = PointUtilities.normalizedEdgeVector(nodeCoord, childCoord);
            }
            return childVectors;
        }

        private Point2D[] generateParentVectors(LatticeElementCollection parents) {
            int parentCount = parents.getSize();
            Point2D[] parentVectors = new Point2D[parentCount];
            for (int parentIndex = 0; parentIndex < parentCount; parentIndex++) {
                LatticeElement parent = parents.get(parentIndex);
                conceptCoordinateMapper.setCoordsForConcept(parent, parentCoord);
                parentVectors[parentIndex] = PointUtilities.normalizedEdgeVector(parentCoord, nodeCoord);
            }
            return parentVectors;
        }
    }
}
