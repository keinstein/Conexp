/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core;

public interface Set extends Cloneable, IPartiallyOrdered {

//-------------------------

    ModifiableSet makeModifiableSetCopy();

//-------------------------

    int NOT_IN_SET = -1;
    /**
     * set of values for return from compare function
     */
    int EQUAL = 0;
    int SUBSET = 1;
    int SUPERSET = 2;
    int NOT_COMPARABLE = 3;
//-----------------------------

    /**
     * compares two sets
     *
     * @return EQUAL if sets are equal
     *         SUBSET if this set is subset of other set
     *         SUPERSET if this set is superset of other set
     *         NOT_COMPARABLE otherwise
     */
    int compare(Set other);

    int lexCompareGanter(Set set);

//-------------------------

    /* return maximal number of elements in set*/
    int size();
//-------------------------

    /**
     * *****************************************************
     * return number of elements in set
     * *****************************************************
     */

    int elementCount();

    int firstIn();

    int firstOut();

    boolean in(int Id);

    int length();

    int nextIn(int prev);

    int nextOut(int prev);

    boolean out(int index);

    int outUpperBound();

    Object clone();

    boolean intersects(Set other);

    boolean isEquals(Set obj);

    boolean isSupersetOf(Set other);

    boolean isSubsetOf(Set s);

    boolean isEmpty();

}
