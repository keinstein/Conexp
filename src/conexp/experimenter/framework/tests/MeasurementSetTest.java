/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/

package conexp.experimenter.framework.tests;

import conexp.experimenter.framework.MeasurementProtocol;
import conexp.experimenter.framework.MeasurementSet;
import junit.framework.TestCase;

public class MeasurementSetTest extends TestCase {
    private static final Class THIS = MeasurementSetTest.class;

    public static void testSetMeasurement() {
        MeasurementSet res = new MeasurementSet();
        res.setMeasurementProtocol(
                MeasurementProtocol.buildMeasurementProtocolFromStrings(
                        new String[][]{{"One", "false"}}
                )
        );

        res.setMeasurement("ONE", "VALUE");
        assertEquals("VALUE", res.getMeasurementValue("ONE"));
        try {
            res.setMeasurement("ONE", "VALUE");
            fail("Measurement can be set only one time");
        } catch (IllegalArgumentException ex) {
            assertTrue(true);
        }

        try {
            res.setMeasurement("two", new Integer(5));
            fail("Can set only measurements in current protocol");
        } catch (IllegalArgumentException ex) {
            assertTrue(true);
        }

        res.setMeasurementProtocol(
                MeasurementProtocol.buildMeasurementProtocolFromStrings(
                        new String[][]{{"two", "true"}}
                )
        );
        try {
            res.setMeasurement("two", new Integer(5));
            assertTrue("should get to here ", true);
        } catch (IllegalArgumentException ex) {
            fail("Protocol can be changed");
        }
    }

}
