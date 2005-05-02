package conexp.core.compareutils;

import java.util.Iterator;

/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 */

public interface KeyValuePairIterator extends Iterator {
    KeyValuePair nextKeyValuePair();
}
