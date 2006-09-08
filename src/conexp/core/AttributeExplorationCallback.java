/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core;

public interface AttributeExplorationCallback {
    int STOP = -1;
    int REJECT = 0;
    int ACCEPT = 1;

    int acceptImplication(Set premise, Set conclusion);
}

