/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/

package conexp.frontend.latticeeditor.labelingstrategies;

import conexp.frontend.latticeeditor.DrawParameters;

import java.awt.geom.Ellipse2D;

class LowerHemisphereUniformPointDistributionStrategy extends SemisphereUniformPointDistributionStrategy {

    public LowerHemisphereUniformPointDistributionStrategy(double centerX, double centerY, int numPoints, DrawParameters opt, Ellipse2D figureEllipse) {
        super(centerX, centerY, numPoints, opt, figureEllipse);
    }

    protected void calcInitialAngle() {
        angle = angleDelta * 0.5;
    }

}
