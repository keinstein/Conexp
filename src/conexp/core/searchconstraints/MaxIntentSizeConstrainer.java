/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.searchconstraints;

import conexp.core.SearchConstraint;
import conexp.core.Set;

public class MaxIntentSizeConstrainer implements SearchConstraint {

    private int maxIntentSize;

    public MaxIntentSizeConstrainer(int minSupport) {
        this.maxIntentSize = minSupport;
    }

    public boolean continueSearch(Set intent, int objectCount) {
        if (intent.elementCount() >= maxIntentSize) {
            return false;
        }
        return true;
    }
}
