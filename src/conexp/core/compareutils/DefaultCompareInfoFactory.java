/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.core.compareutils;

public class DefaultCompareInfoFactory implements CompareInfoFactory {
    public CompareInfo makeCompareInfo(Object obj, int type) {
        return new CompareInfo(obj, type);
    }
}
