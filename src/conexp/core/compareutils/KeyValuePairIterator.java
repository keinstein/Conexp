/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.core.compareutils;

import java.util.Iterator;



public interface KeyValuePairIterator extends Iterator {
    KeyValuePair nextKeyValuePair();
}
