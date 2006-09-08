/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/

package conexp.experimenter.experiments;

import conexp.core.compareutils.IComparatorFactory;
import conexp.experimenter.framework.IExperiment;
import conexp.experimenter.framework.IMeasurementDescription;
import conexp.experimenter.framework.IMeasurementProtocol;
import conexp.experimenter.framework.MeasurementDescription;
import conexp.experimenter.framework.MeasurementProtocol;
import conexp.experimenter.framework.MeasurementSet;
import util.ReflectHelper;
import util.StringUtil;

public abstract class BasicExperiment implements IExperiment {

    protected static Object createClassByName(String name) {
        return ReflectHelper.createClassByName(name);
    }

    protected static IMeasurementDescription makeUsualMeasurement(String name) {
        return new MeasurementDescription(name, false);
    }

    protected static IMeasurementDescription makeValidatingMeasurement(String name) {
        return new MeasurementDescription(name, true);
    }

    protected static IMeasurementDescription makeValidatingMeasurement(String name, IComparatorFactory comparatorFactory) {
        final MeasurementDescription measurementDescription = new MeasurementDescription(name, true);
        measurementDescription.setComparatorFactory(comparatorFactory);
        return measurementDescription;
    }

    protected abstract Object getActionObject();

    public String getDescription() {
        return StringUtil.extractClassName(getActionObject().getClass().toString());
    }

    protected MeasurementProtocol measurementProtocol;

    public final IMeasurementProtocol getMeasurementProtocol() {
        if (null == measurementProtocol) {
            measurementProtocol = new MeasurementProtocol();
            declareMeasures(measurementProtocol);
        }
        return measurementProtocol;
    }

    protected void declareMeasures(MeasurementProtocol protocol) {
    }

    public void saveResults(MeasurementSet results) {
    }

}
