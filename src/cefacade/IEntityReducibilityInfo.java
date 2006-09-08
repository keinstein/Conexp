/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package cefacade;

import java.util.Collection;
import java.util.List;



public interface IEntityReducibilityInfo {
    boolean isIrreducible();

    List getClassOfEquivalence();

    Collection getReducingClasses();

}
