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
import util.gui.GraphicObjectsFactory;

import java.awt.geom.Point2D;



public class NumberOfSimmetricallyAllocatedChildrenEvaluationFunction extends LatticeBasedEvaluationFunctionBase {
    public NumberOfSimmetricallyAllocatedChildrenEvaluationFunction() {
    }

    public NumberOfSimmetricallyAllocatedChildrenEvaluationFunction(Lattice lattice, ConceptCoordinateMapper conceptCoordinateMapper) {
        super(lattice, conceptCoordinateMapper);
    }

    public static final double MINIMAL_PRECISION = 1e-4;
    //the bigger value of evaluation function is better

    public double getEvaluationForLattice() {
        SymmetryCalculatorLatticeElementVisitor visitor = new SymmetryCalculatorLatticeElementVisitor();
        lattice.forEach(visitor);
        return visitor.getSymmetriesCount();
    }

    private class SymmetryCalculatorLatticeElementVisitor implements Lattice.LatticeElementVisitor {
        int childSymmetry;
        int parentSymmetry;
        Point2D firstChildCoord = GraphicObjectsFactory.makePoint2D();
        Point2D otherChildCoord = GraphicObjectsFactory.makePoint2D();
        Point2D nodeCoord = GraphicObjectsFactory.makePoint2D();

        public void visitNode(LatticeElement node) {
            conceptCoordinateMapper.setCoordsForConcept(node, nodeCoord);
            childSymmetry += calculateSimmetriesForSiblings(node.getSuccessors());
            parentSymmetry += calculateSimmetriesForSiblings(node.getPredecessors());
        }

        public int getSymmetriesCount() {
            return childSymmetry + parentSymmetry;
        }

        public int getChildSymmetry() {
            return childSymmetry;
        }

        public int getParentSymmetry() {
            return parentSymmetry;
        }

        private int calculateSimmetriesForSiblings(LatticeElementCollection siblings) {
            int childCount = siblings.getSize();
            int simmetries = 0;
            for (int i = 0; i < childCount; i++) {
                LatticeElement firstChild = siblings.get(i);
                conceptCoordinateMapper.setCoordsForConcept(firstChild, firstChildCoord);
                double firstChildXDistanceToParent = nodeCoord.getX() - firstChildCoord.getX();
                if (Math.abs(firstChildXDistanceToParent) < MINIMAL_PRECISION) {
                    if (childCount % 2 == 1) {
                        simmetries++;
                    }
                    continue;
                }
                for (int j = i + 1; j < childCount; j++) {
                    LatticeElement otherChild = siblings.get(j);
                    conceptCoordinateMapper.setCoordsForConcept(otherChild, otherChildCoord);
                    if (Math.abs(firstChildCoord.getY() - otherChildCoord.getY()) > MINIMAL_PRECISION) {
                        continue;
                    }
                    double otherChildXDistanceToParent = nodeCoord.getX() - otherChildCoord.getX();
                    if (Math.abs(firstChildXDistanceToParent + otherChildXDistanceToParent) < MINIMAL_PRECISION) {
                        simmetries += 2;
                    }
                }
            }
            return simmetries;
        }
    }
}
