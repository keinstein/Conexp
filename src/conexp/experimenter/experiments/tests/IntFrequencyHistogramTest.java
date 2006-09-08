package conexp.experimenter.experiments.tests;

import conexp.experimenter.experiments.IntFrequencyHistogram;
import junit.framework.TestCase;

import java.util.Arrays;

/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 * Date: 6/10/2003
 * Time: 23:54:14
 */

public class IntFrequencyHistogramTest extends TestCase {
    public static void testFrequencyHistogram() {
        IntFrequencyHistogram histogram = new IntFrequencyHistogram("Extents sizes");
        histogram.putValue(0);
        assertEquals(1, histogram.getFrequency(0));
        assertEquals(0, histogram.getFrequency(1));
        histogram.putValue(-1);
        histogram.putValue(-1);
        assertEquals(2, histogram.getFrequency(-1));
        histogram.putValue(2);
        histogram.putValue(-3);

        int[] values = histogram.getValues();
        assertTrue(Arrays.equals(new int[]{-3, -1, 0, 2}, values));
        System.out.println(histogram);
    }
}
