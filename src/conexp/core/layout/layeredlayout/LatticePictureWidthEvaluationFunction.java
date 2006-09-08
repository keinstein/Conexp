/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.core.layout.layeredlayout;

import conexp.core.Lattice;
import conexp.core.LatticeElement;
import conexp.core.layout.ConceptCoordinateMapper;
import util.gui.GraphicObjectsFactory;

import java.awt.geom.Point2D;


public class LatticePictureWidthEvaluationFunction extends LatticeBasedEvaluationFunctionBase {

    public LatticePictureWidthEvaluationFunction(Lattice lattice, ConceptCoordinateMapper conceptCoordinateMapper) {
        super(lattice, conceptCoordinateMapper);
    }

    //the bigger value of evaluation function is better
    public double getEvaluationForLattice() {
        final LatticeWidthCalculatorElementVisitor visitor = new LatticeWidthCalculatorElementVisitor();
        lattice.forEach(visitor);
        return -visitor.getWidth();
    }

    private class LatticeWidthCalculatorElementVisitor implements Lattice.LatticeElementVisitor {
        double minX = Double.NaN;
        double maxX = Double.NaN;
        boolean empty = true;
        Point2D coords = GraphicObjectsFactory.makePoint2D();

        public void visitNode(LatticeElement node) {
            conceptCoordinateMapper.setCoordsForConcept(node, coords);
            final double xCoord = coords.getX();
            if (empty) {
                empty = false;
                minX = maxX = xCoord;
            } else {
                minX = Math.min(minX, xCoord);
                maxX = Math.max(maxX, xCoord);
            }
        }

        public double getWidth() {
            return maxX - minX;
        }

    }
}
