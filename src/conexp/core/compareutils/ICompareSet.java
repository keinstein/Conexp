/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.core.compareutils;


public interface ICompareSet {

    /**
     * Insert the method's description here.
     * Creation date: (13.07.01 19:08:13)
     * @return conexp.core.compareutils.KeyValuePair
     * @param index int
     */
    KeyValuePair get(int index);


    /**
     * Insert the method's description here.
     * Creation date: (13.07.01 19:04:49)
     * @return int
     */
    int getSize();
}
