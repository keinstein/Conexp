/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.associations;

import conexp.core.Context;
import conexp.core.ExtendedContextEditingInterface;
import conexp.core.Set;

public class NullFrequentSetSupportSupplier implements FrequentSetSupportSupplier {
    private ExtendedContextEditingInterface cxt = new Context(0, 0);

    public int supportForSet(Set attribs) {
        return 0;
    }

    public int supportForClosedSet(Set attribs) {
        return 0;
    }

    public int getTotalObjectCount() {
        return 0;
    }

    public ExtendedContextEditingInterface getDataSet() {
        return cxt;
    }

}
