/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/

package conexp.experimenter.framework;

import conexp.core.compareutils.IComparatorFactory;

public class MeasurementDescription implements IMeasurementDescription {
    final String name;
    final boolean validating;
    IComparatorFactory comparatorFactory;

    public MeasurementDescription(String name, boolean validating) {
        this.name = name;
        this.validating = validating;
    }

    public boolean isValidating() {
        return validating;
    }

    public String getName() {
        return name;
    }

    public IComparatorFactory getComparatorFactory() {
        return comparatorFactory;
    }

    public void setComparatorFactory(IComparatorFactory comparatorFactory) {
        this.comparatorFactory = comparatorFactory;
    }
}
