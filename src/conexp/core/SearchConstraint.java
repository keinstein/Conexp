/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.core;


public interface SearchConstraint {
    public boolean continueSearch(Set intent, int objectCount);
}
