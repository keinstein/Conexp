package conexp.core.compareutils;

import conexp.core.compareutils.BaseComparator;

/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 * Date: 6/8/2003
 * Time: 15:38:25
 */

public interface IComparatorFactory {
    BaseComparator createComparator(Object one, Object two);
}
