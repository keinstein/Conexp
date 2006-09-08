/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core;

public interface BinaryRelation {

    int getRowCount();

    int getColCount();

    /**
     * gets value of cell with coordinates (x, y)
     *
     * @param row - x  coordinate
     * @param col - y coordinate
     * @return value
     */

    boolean getRelationAt(int row, int col);

    /**
     * returns Set of values
     *
     * @param row
     */
    Set getSet(int row);

    ModifiableBinaryRelation makeModifiableCopy();
}
