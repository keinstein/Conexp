/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.compareutils;


public interface CompareInfoFactory {
    /**
     * Insert the method's description here.
     * Creation date: (13.07.01 16:43:33)
     *
     * @param obj  java.lang.Object
     * @param type int
     */
    CompareInfo makeCompareInfo(Object key, Object obj, int type);
}
