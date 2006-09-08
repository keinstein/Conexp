/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.layout.tests;

import conexp.core.Lattice;
import conexp.core.LatticeElement;
import conexp.core.layout.DefaultLayoutParameters;
import conexp.core.layout.Layouter;
import conexp.core.layout.SimpleForceLayout;
import conexp.core.tests.SetBuilder;
import conexp.util.valuemodels.BoundedDoubleValue;
import util.testing.SimpleMockPropertyChangeListener;

import java.awt.geom.Point2D;


public abstract class SimpleForceLayoutTest extends GenericLayouterTest {
    protected boolean isTestImproveOnce() {
        return true;
    }

    public void testAngleChange() {
        Lattice lat = SetBuilder.makeLatticeWithContext(new int[][]{{1, 0, 0},
                {1, 1, 0},
                {0, 0, 1}});

        SimpleForceLayout layouter = makeLayouterAndPerformLayout(lat);
        SimpleMockPropertyChangeListener listener = new SimpleMockPropertyChangeListener(Layouter.LAYOUT_CHANGE);
        layouter.addLayoutChangeListener(listener);
        listener.setExpected(1);
        layouter.getRotationAngle().setValue(Math.PI / 2);
        listener.verify();
    }

    public void testForceDistributionParamChange() {

        Lattice lat = SetBuilder.makeLatticeWithContext(new int[][]{{1, 0, 0},
                {1, 1, 0},
                {0, 0, 1}});

        SimpleForceLayout layouter = makeLayouterAndPerformLayout(lat);
        SimpleMockPropertyChangeListener listener = new SimpleMockPropertyChangeListener(Layouter.LAYOUT_PARAMS_CHANGE);
        layouter.addLayoutChangeListener(listener);

        listener.setExpected(1);
        layouter.getForceDistribution().getAttractionFactorModel().setValue(0.2);
        listener.verify();

        listener.setExpected(1);
        layouter.getForceDistribution().getRepulsionFactorModel().setValue(0.2);
        listener.verify();
    }

    private SimpleForceLayout makeLayouterAndPerformLayout(Lattice lat) {
        SimpleForceLayout layouter = (SimpleForceLayout) makeLayouter();
        layouter.initLayout(lat, new DefaultLayoutParameters());
        layouter.performLayout();
        return layouter;
    }

    public void testLatticeCoordsWithMaximalAttraction() {
        final int[][] relation = new int[][]{{1, 0},
                {0, 1}};

        Lattice lat = SetBuilder.makeLatticeWithContext(relation);
        final SimpleForceLayout layouter = (SimpleForceLayout) makeLayouter();
        BoundedDoubleValue attractionFactorModel = layouter.getForceDistribution().getAttractionFactorModel();
        attractionFactorModel.setValue(attractionFactorModel.maxVal);
        layouter.initLayout(lat, new DefaultLayoutParameters());
        layouter.performLayout();

        lat.forEach(new Lattice.LatticeElementVisitor() {
            public void visitNode(LatticeElement node) {
                Point2D coords = new Point2D.Double();
                layouter.setCoordsForConcept(node, coords);
                checkCoordsAreNotNaN(coords);
            }
        });

    }
}
