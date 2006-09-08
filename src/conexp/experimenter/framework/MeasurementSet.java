package conexp.experimenter.framework;

import java.util.HashMap;
import java.util.Map;

/**
 * Insert the type's description here.
 * Creation date: (17.07.01 19:50:09)
 *
 * @author
 */
public class MeasurementSet {
    ValidityStatistics validityStatistics;
    Map resultDictionary = new HashMap();
    IMeasurementProtocol currentProtocol;


    public void setMeasurementProtocol(IMeasurementProtocol protocol) {
        currentProtocol = protocol;
    }

    public void clearMeasurementProtocol() {
        currentProtocol = null;
    }

    public MeasurementSet() {

    }

    public void setMeasurement(String name, Object value) {
        if (null != resultDictionary.get(name)) {
            throw new IllegalArgumentException("Measurement " + name + " was alredy set");
        }
        if (!currentProtocol.hasMeasurementWithName(name)) {
            throw new IllegalArgumentException("Error in measurement name " + name + ". Measurement isn't declared in protocol");
        }
        resultDictionary.put(name, value);
    }

    public void setMeasurement(String name, int value) {
        setMeasurement(name, new Integer(value));
    }

    public Object getMeasurementValue(String name) {
        return resultDictionary.get(name);
    }


    public void setValidityStatistics(ValidityStatistics valStat) {
        validityStatistics = valStat;
    }
}