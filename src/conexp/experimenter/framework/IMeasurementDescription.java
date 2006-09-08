/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/

package conexp.experimenter.framework;

import conexp.core.compareutils.IComparatorFactory;


public interface IMeasurementDescription {
    String getName();

    boolean isValidating();

    IComparatorFactory getComparatorFactory();
}
