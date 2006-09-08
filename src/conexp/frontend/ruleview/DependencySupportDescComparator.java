/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.ruleview;

import conexp.core.Dependency;

import java.util.Comparator;


public class DependencySupportDescComparator implements Comparator {
    protected static java.util.Comparator comparator;

    public int compare(Object o1, Object o2) {
        return (((Dependency) o2).getRuleSupport() - ((Dependency) o1).getRuleSupport());
    }


    /**
     * Insert the method's description here.
     * Creation date: (07.05.01 22:00:57)
     *
     * @return java.util.Comparator
     */
    public static Comparator getComparator() {
        if (null == comparator) {
            comparator = new DependencySupportDescComparator();
        }
        return comparator;
    }
}
