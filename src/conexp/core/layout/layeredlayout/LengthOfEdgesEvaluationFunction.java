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


public class LengthOfEdgesEvaluationFunction extends LatticeBasedEvaluationFunctionBase {
    public LengthOfEdgesEvaluationFunction() {
    }

    public LengthOfEdgesEvaluationFunction(Lattice lattice, ConceptCoordinateMapper conceptCoordinateMapper) {
        super(lattice, conceptCoordinateMapper);
    }

    //the bigger value of evaluation function is better
    public double getEvaluationForLattice() {
        EdgeSumCalculatorElementVisitor visitor = new EdgeSumCalculatorElementVisitor();
        lattice.forEach(visitor);
        return -visitor.getSum();
    }

    private class EdgeSumCalculatorElementVisitor implements Lattice.LatticeElementVisitor {
        double sum = 0;

        public double getSum() {
            return sum;
        }

        Point2D nodePoint = GraphicObjectsFactory.makePoint2D();
        Point2D childPoint = GraphicObjectsFactory.makePoint2D();

        public void visitNode(LatticeElement node) {
            conceptCoordinateMapper.setCoordsForConcept(node, nodePoint);
            LatticeElementCollection successors = node.getSuccessors();
            for (int i = successors.getSize(); --i >= 0;) {
                LatticeElement child = successors.get(i);
                conceptCoordinateMapper.setCoordsForConcept(child, childPoint);
                sum += nodePoint.distance(childPoint);
            }
        }
    }
}
