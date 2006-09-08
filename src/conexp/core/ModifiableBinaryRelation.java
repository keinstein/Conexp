/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core;

public interface ModifiableBinaryRelation extends BinaryRelation {
    void clearRelation();

    void removeCol(int col);

    void removeRow(int row);

    void setDimension(int rows, int cols);

    /**
     * set value of cell with coordinates (x, y)
     *
     * @param x     - x  coordinate
     * @param y     - y coordinate
     * @param value
     */
    void setRelationAt(int x, int y, boolean value);
    //ModifiableBinaryRelation

    ModifiableSet getModifiableSet(int j);

    void addSet(Set set);
}
