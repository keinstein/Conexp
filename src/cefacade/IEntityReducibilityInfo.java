package cefacade;

import java.util.Collection;
import java.util.List;

/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 */

public interface IEntityReducibilityInfo {
    boolean isIrreducible();

    List getClassOfEquivalence();

    Collection getReducingClasses();

}
