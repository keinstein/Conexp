/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.core.searchconstraints;

import conexp.core.SearchConstraint;
import conexp.core.Set;

public class MinSupportConstrainer implements SearchConstraint {

    int minSupport;

    public MinSupportConstrainer(int minSupport) {
        this.minSupport = minSupport;
    }

    public boolean continueSearch(Set intent, int objectCount) {
        if (objectCount < minSupport) {
            return false;
        }
        return true;
    }
}
