/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core;


public interface SearchConstraint {
    boolean continueSearch(Set intent, int objectCount);
}
