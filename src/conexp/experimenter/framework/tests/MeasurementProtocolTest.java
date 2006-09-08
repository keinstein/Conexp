/*
 * Created by IntelliJ IDEA.
 * User: sergey
 * Date: Oct 20, 2001
 * Time: 5:03:19 PM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package conexp.experimenter.framework.tests;

import com.mockobjects.ExpectationSet;
import conexp.experimenter.framework.IMeasurementDescription;
import conexp.experimenter.framework.IMeasurementProtocol;
import conexp.experimenter.framework.MeasurementDescription;
import conexp.experimenter.framework.MeasurementProtocol;
import junit.framework.TestCase;

import java.util.Iterator;

public class MeasurementProtocolTest extends TestCase {

    public static void testAddMeasurement() {
        MeasurementProtocol protocol = new MeasurementProtocol();
        protocol.addMeasurement(new MeasurementDescription("One", false));
        assertTrue(protocol.hasMeasurementWithName("One"));
        assertTrue("Comparison of measurements names is case insensetive", protocol.hasMeasurementWithName("ONE"));
        assertEquals(false, protocol.hasMeasurementWithName("Two"));


        try {
            protocol.addMeasurement(new MeasurementDescription("One", true));
            fail("In protocol can be measurements only with different names");
        } catch (IllegalArgumentException ex) {
            assertTrue(true);
        }
        protocol.addMeasurement(new MeasurementDescription("Two", true));
        protocol.addMeasurement(new MeasurementDescription("Three", false));
        protocol.addMeasurement(new MeasurementDescription("Four", true));


        checkProtocolForExpectedTypesOfMeasures(protocol,
                new String[]{"One", "Two", "Three", "Four"},
                new String[]{"Two", "Four"});

    }

    private static void checkProtocolForExpectedTypesOfMeasures(IMeasurementProtocol protocol, String[] allMeasuresNames, String[] verifiedMeasuresNames) {
        ExpectationSet expectedValidatedMeasurements = makeExpectationSet("Expected validated measurements", verifiedMeasuresNames);
        fillExpectationSetWithMeasurementsNames(expectedValidatedMeasurements, protocol.validatingMeasurementIterator());
        expectedValidatedMeasurements.verify();

        ExpectationSet expectedAllMeasurementsNames = makeExpectationSet("Expected measurements names",
                allMeasuresNames);
        fillExpectationSetWithMeasurementsNames(expectedAllMeasurementsNames, protocol.measurementsIterator());
        expectedAllMeasurementsNames.verify();
    }

    private static void fillExpectationSetWithMeasurementsNames(ExpectationSet expectedValidatedMeasurements, Iterator validatedMeasurementIter) {
        while (validatedMeasurementIter.hasNext()) {
            IMeasurementDescription descriptor = (IMeasurementDescription) validatedMeasurementIter.next();
            expectedValidatedMeasurements.addActual(descriptor.getName());
        }
    }

    private static ExpectationSet makeExpectationSet(String expectationSetName, String[] validatedMeasurementsNames) {
        ExpectationSet expectedValidatedMeasurements = new ExpectationSet(expectationSetName);

        for (int i = 0; i < validatedMeasurementsNames.length; i++) {
            expectedValidatedMeasurements.addExpected(validatedMeasurementsNames[i]);
        }
        return expectedValidatedMeasurements;
    }

    public static void testBuildFromStrings() {
        IMeasurementProtocol protocol = MeasurementProtocol.buildMeasurementProtocolFromStrings(new String[][]{
                {"One", "false"},
                {"Two", "true"},
                {"Three", "false"},
                {"Four", "true"},
        });

        checkProtocolForExpectedTypesOfMeasures(protocol,
                new String[]{"One", "Two", "Three", "Four"},
                new String[]{"Two", "Four"});

    }

}
