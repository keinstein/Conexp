/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.compareutils;


public class KeyValuePair {
    public final Object key;
    public final Object value;

    public KeyValuePair(Object k, Object v) {
        super();
        key = k;
        value = v;
    }

    public String toString() {
        return "Key: [" + key + "] Value: [" + value + ']';
    }

}
