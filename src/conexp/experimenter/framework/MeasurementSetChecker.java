/*
 * Created by IntelliJ IDEA.
 * User: sergey
 * Date: Oct 20, 2001
 * Time: 8:53:30 PM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package conexp.experimenter.framework;

import conexp.core.compareutils.BaseComparator;
import research.conexp.core.compareutils.IComparatorFactory;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MeasurementSetChecker {
    Map validatingParamsMap = new HashMap();

    public void clear() {
        validatingParamsMap.clear();
    }

    void checkValidatingParamsValues(MeasurementSet expResults, IExperiment experiment) throws ExperimentException {
        IMeasurementProtocol measurementProtocol = experiment.getMeasurementProtocol();
        Iterator validatingParamsIter = measurementProtocol.validatingMeasurementIterator();
        while (validatingParamsIter.hasNext()) {
            IMeasurementDescription paramDescription = (IMeasurementDescription) validatingParamsIter.next();
            Object experimentValue = expResults.getMeasurementValue(paramDescription.getName());
            if (validatingParamsMap.containsKey(paramDescription.getName())) {
                Object paramValue = validatingParamsMap.get(paramDescription.getName());
                final IComparatorFactory comparatorFactory = paramDescription.getComparatorFactory();
                if (comparatorFactory != null) {
                    BaseComparator comparator = comparatorFactory.createComparator(paramValue, experimentValue);
                    if(!comparator.isEqual()){
                        StringWriter writer = new StringWriter();
                        final PrintWriter printWriter = new PrintWriter(writer);
                        comparator.writeReport(printWriter);
                        printWriter.close();
                        throw new ExperimentException("For param " + paramDescription.getName() + " values of two runs are different "+
                                writer.toString());
                    }
                } else {
                    if (!paramValue.equals(experimentValue)) {
                        throw new ExperimentException("For param " + paramDescription.getName() + " values of two runs are different " +
                                paramValue + " ne results of " + experiment.getDescription() + " : " + experimentValue);
                    }
                }
            } else {
                validatingParamsMap.put(paramDescription.getName(), experimentValue);
            }
        }
    }

}
