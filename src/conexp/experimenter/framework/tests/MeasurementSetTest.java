/*
 * Created by IntelliJ IDEA.
 * User: sergey
 * Date: Oct 20, 2001
 * Time: 5:03:19 PM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package conexp.experimenter.framework.tests;

import conexp.experimenter.framework.MeasurementProtocol;
import conexp.experimenter.framework.MeasurementSet;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

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
