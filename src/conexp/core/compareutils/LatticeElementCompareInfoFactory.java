/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.compareutils;


public class LatticeElementCompareInfoFactory implements CompareInfoFactory {
    /**
     * Insert the method's description here.
     * Creation date: (13.07.01 18:40:40)
     *
     * @param obj  java.lang.Object
     * @param type int
     */
    public CompareInfo makeCompareInfo(Object key, Object obj, int type) {
        return new LatticeElementCompareInfo(key, obj, type);
    }
}
