/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor.labelingstrategies;

import conexp.core.layout.LayoutParameters;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

public abstract class SemisphereUniformPointDistributionStrategy implements PointDistributionStrategy {
    double angleDelta;
    double angle;
    double centerX;
    double centerY;
    LayoutParameters opt;
    Ellipse2D figureEllipse;

    protected SemisphereUniformPointDistributionStrategy(double centerX, double centerY, int numPoints, LayoutParameters opt, Ellipse2D figureEllipse) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.opt = opt;
        angleDelta = Math.PI / numPoints;
        calcInitialAngle();
        this.figureEllipse = figureEllipse;
    }

    protected double getCenterX() {
        return centerX;
    }

    protected double getCenterY() {
        return centerY;
    }

    protected double getDiameterX() {
        return figureEllipse.getWidth();
    }

    protected double getDiameterY() {
        return figureEllipse.getHeight();
    }

    protected abstract void calcInitialAngle();

    public void setNextCoords(Point2D coords) {
        double offX = (getDiameterX() + opt.getGridSizeX()) / 2 * Math.cos(angle);
        double offY = (1.5 * getDiameterY() + opt.getGridSizeY()) / 3 * Math.sin(angle);

        coords.setLocation(getCenterX() + offX, getCenterY() + offY);
        angle += angleDelta;
    }

}
