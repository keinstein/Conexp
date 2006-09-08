/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.layout.tests;

import conexp.core.layout.ForceDistribution;
import conexp.util.valuemodels.BoundedDoubleValue;
import junit.framework.TestCase;


public class ForceDistributionTest extends TestCase {
    private ForceDistribution forceDistrib;

    public void testStability() {
        forceDistrib.init(2);
        for (int i = 0; i < 200; i++) {
            forceDistrib.forceConstantsForIterations(i);
        }
        forceDistrib.forceConstantsForIterations(-5);
    }

    protected void setUp() {
        forceDistrib = new ForceDistribution();
    }

    public void testAcceptabilityOfValues() {
        final BoundedDoubleValue attractionFactorModel = forceDistrib.getAttractionFactorModel();
        attractionFactorModel.setValue(attractionFactorModel.maxVal);

        doTestAcceptabilityOfAttractionFactor();

        attractionFactorModel.setValue(attractionFactorModel.minVal);
        doTestAcceptabilityOfAttractionFactor();


    }

    private void doTestAcceptabilityOfAttractionFactor() {
        forceDistrib.init(2);
        for (int i = 0; i < 200; i++) {
            float[] forceConstants = forceDistrib.forceConstantsForIterations(i);
            final float attractionFactor = forceConstants[0];
            assertTrue(attractionFactor > 0);
            assertTrue(attractionFactor < 1);
        }
    }
}
