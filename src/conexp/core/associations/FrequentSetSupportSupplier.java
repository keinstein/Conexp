/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.associations;

import conexp.core.ExtendedContextEditingInterface;
import conexp.core.Set;


public interface FrequentSetSupportSupplier {
    int supportForSet(Set attribs);

    int supportForClosedSet(Set attribs);

    int getTotalObjectCount();

    //todo: move to general notion of rule  (as consisiting from hypothesis as some expressions) and
    //general notion of dataset (as collection of objects, according to which hypothesis can be tested)
    ExtendedContextEditingInterface getDataSet(); //for now
}
