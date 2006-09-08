/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor.labelingstrategies;

import conexp.core.layout.LayoutParameters;

import java.awt.geom.Ellipse2D;

class UpperHemisphereUniformPointDistributionStrategy extends SemisphereUniformPointDistributionStrategy {

    public UpperHemisphereUniformPointDistributionStrategy(double centerX, double centerY, int numPoints, LayoutParameters opt, Ellipse2D figureEllipse) {
        super(centerX, centerY, numPoints, opt, figureEllipse);
    }

    protected void calcInitialAngle() {
        angle = Math.PI + angleDelta * 0.5;
    }

}
