/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.frontend.latticeeditor;

import conexp.frontend.latticeeditor.figures.AbstractConceptCorrespondingFigure;
import conexp.frontend.latticeeditor.figures.DefaultFigureVisitor;



public class RescaleByWeigthVisitor extends DefaultFigureVisitor {
    double startY;
    double endY;
    double weightFactor;

    public RescaleByWeigthVisitor(double startY, double endY, double weightFactor) {
        this.startY = startY;
        this.endY = endY;
        this.weightFactor = weightFactor;
    }

    public void visitConceptCorrespondingFigure(AbstractConceptCorrespondingFigure f) {
        double centerX = f.getCenterX();
        double newY = endY + f.getConcept().getObjCnt() * weightFactor * (startY - endY);
        f.setCoords(centerX, newY);
    }
}
