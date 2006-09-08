/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.searchconstraints;

import conexp.core.SearchConstraint;
import conexp.core.Set;

public class NullSearchConstraint implements SearchConstraint {
    public boolean continueSearch(Set intent, int objectCount) {
        return true;
    }
}
