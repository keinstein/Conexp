/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core;

public interface ModifiableSet extends Set {
//-------------------------

    /**
     * puts element in set
     */

    void put(int elId);

//-------------------------

    /**
     * removes element from set
     */
    void remove(int elId);

    /**
     * removes all elements from set
     */
    void clearSet();

    /**
     * puts in set elements with id less and equal to till
     */

    void fill();

    /**
     * performs inplace or operation
     */

    void or(Set set);


    /**
     * resizes set
     * Creation date: (22.07.01 0:05:07)
     *
     * @param newSize int
     */
    void resize(int newSize);

    /**
     * removes element from set
     * and decreases set capacity
     */
    void exclude(int elId);

    /**
     * Insert the method's description here.
     * Creation date: (16.10.00 23:51:20)
     *
     * @param addition conexp.core.Set
     */
    void append(Set addition);

    void copy(Set set);

    /**
     * performs inplace and operation
     */
    void and(Set set);

    /**
     * performs inplace andNot operation
     */
    void andNot(Set set);
}
