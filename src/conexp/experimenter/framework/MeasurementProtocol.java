/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/

package conexp.experimenter.framework;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class MeasurementProtocol implements IMeasurementProtocol {
    protected Collection measurementsDescriptors = new ArrayList();
    protected Collection validatedMeasurements = new ArrayList();


    public MeasurementProtocol() {

    }

    public void addMeasurement(IMeasurementDescription description) throws IllegalArgumentException {
        if (hasMeasurementWithName(description.getName())) {
            throw new IllegalArgumentException("Measurement with name " + description.getName() + " already was added");
        }
        measurementsDescriptors.add(description);
        if (description.isValidating()) {
            validatedMeasurements.add(description);
        }
    }

    public boolean hasMeasurementWithName(String name) {
        Iterator iter = measurementsIterator();
        while (iter.hasNext()) {
            IMeasurementDescription descriptor = (IMeasurementDescription) iter.next();
            if (name.equalsIgnoreCase(descriptor.getName())) {
                return true;
            }
        }
        return false;
    }

    public Iterator measurementsIterator() {
        return measurementsDescriptors.iterator();
    }

    public Iterator validatingMeasurementIterator() {
        return validatedMeasurements.iterator();
    }

    static boolean checkValidatingParam(String param) {
        if (!"true".equalsIgnoreCase(param) &&
                !"false".equalsIgnoreCase(param)) {
            return false;
        }
        return true;
    }

    public static MeasurementProtocol buildMeasurementProtocolFromStrings(String[][] descriptions) throws IllegalArgumentException {
        MeasurementProtocol ret = new MeasurementProtocol();

        for (int i = 0; i < descriptions.length; i++) {
            final String paramName = descriptions[i][0];
            final String validatingString = descriptions[i][1];
            if (!checkValidatingParam(validatingString)) {
                throw new IllegalArgumentException("Param " + paramName + " for creating measurement protocol is bad in position " + i);
            }

            boolean validating = "true".equalsIgnoreCase(validatingString);
            ret.addMeasurement(new MeasurementDescription(paramName, validating));
        }
        return ret;

    }
}
