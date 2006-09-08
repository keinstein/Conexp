/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.compareutils;

public class DefaultCompareInfoFactory implements CompareInfoFactory {

    private static CompareInfoFactory g_Instance = new DefaultCompareInfoFactory();

    public static CompareInfoFactory getInstance() {
        return g_Instance;
    }

    public CompareInfo makeCompareInfo(Object key, Object obj, int type) {
        return new CompareInfo(key, obj, type);
    }
}
