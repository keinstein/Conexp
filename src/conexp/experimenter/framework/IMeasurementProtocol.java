/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/

package conexp.experimenter.framework;

import java.util.Iterator;

public interface IMeasurementProtocol {
    boolean hasMeasurementWithName(String name);

    Iterator measurementsIterator();

    Iterator validatingMeasurementIterator();
}
