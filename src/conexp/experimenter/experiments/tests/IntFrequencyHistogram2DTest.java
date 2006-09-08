/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.experimenter.experiments.tests;

import conexp.experimenter.experiments.IntFrequencyHistogram2D;
import junit.framework.TestCase;

import java.util.Arrays;



public class IntFrequencyHistogram2DTest extends TestCase {
    public static void testFrequencyHistogram() {
        IntFrequencyHistogram2D histogram = new IntFrequencyHistogram2D("IntentExtentDistribution");
        histogram.putValue(0, 0);
        assertEquals(1, histogram.getFrequency(0, 0));
        assertEquals(0, histogram.getFrequency(1, 0));
        histogram.putValue(-1, 0);
        histogram.putValue(-1, 0);
        assertEquals(2, histogram.getFrequency(-1, 0));
        histogram.putValue(2, 0);
        histogram.putValue(-3, 1);

        int[] values = histogram.getXValues();
        assertTrue(Arrays.equals(new int[]{-3, -1, 0, 2}, values));
    }

}
