package conexp.frontend.latticeeditor.labelingstrategies;

import conexp.frontend.latticeeditor.DrawParameters;

import java.awt.geom.Ellipse2D;

class UpperHemisphereUniformPointDistributionStrategy extends SemisphereUniformPointDistributionStrategy {

    public UpperHemisphereUniformPointDistributionStrategy(double centerX, double centerY, int numPoints, DrawParameters opt, Ellipse2D figureEllipse) {
        super(centerX, centerY, numPoints, opt, figureEllipse);
    }

    protected void calcInitialAngle() {
        angle = Math.PI + angleDelta * 0.5;
    }

}
