/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.associations;

import conexp.core.BinaryRelation;
import conexp.core.ExtendedContextEditingInterface;
import conexp.core.Set;

public class ContextFrequentSetSupportSupplier implements FrequentSetSupportSupplier {
    private ExtendedContextEditingInterface cxt;

    public ContextFrequentSetSupportSupplier(ExtendedContextEditingInterface cxt) {
        super();
        this.cxt = cxt;
    }

    public int supportForSet(Set attribs) {
        BinaryRelation rel = cxt.getRelation();
        int ret = 0;
        for (int i = rel.getRowCount(); --i >= 0;) {
            if (attribs.isSubsetOf(rel.getSet(i))) {
                ret++;
            }
        }
        return ret;
    }

    public int supportForClosedSet(Set attribs) {
        return supportForSet(attribs);
    }

    public int getTotalObjectCount() {
        return cxt.getObjectCount();
    }

    public ExtendedContextEditingInterface getDataSet() //for now
    {
        return cxt;
    }

}
