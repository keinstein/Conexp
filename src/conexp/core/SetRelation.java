/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core;

import util.Assert;

import java.io.PrintWriter;
import java.io.StringWriter;

public class SetRelation implements ModifiableBinaryRelation {
// implementation of conexp.core.BinaryRelation interface
    private ModifiableSet[] relation;
    private int sizeX;
    private int sizeY;

    public SetRelation(int _sizeX, int _sizeY) {
        super();
        setDimension(_sizeX, _sizeY);
    }

    public void clearRelation() {
        for (int j = sizeX; --j >= 0;) {
            relation[j].clearSet();
        }
    }

    public synchronized ModifiableBinaryRelation makeModifiableCopy() {
        SetRelation ret = new SetRelation(sizeX, sizeY);
        for (int i = 0; i < sizeX; i++) {
            ret.getModifiableSet(i).copy(getSet(i));
        }
        return ret;
    }

    /**
     * Insert the method's description here.
     * Creation date: (09.03.01 23:22:43)
     */
    public Object clone() {
        return makeModifiableCopy();
    }

    public synchronized int getColCount() {
        return sizeY;
    }

    /**
     * @param x x-coordinate
     * @param y y-coordinate
     * @return value of cell with coordinates(x,y)
     */
    public boolean getRelationAt(int x, int y) {
        return relation[x].in(y);
    }

    public synchronized int getRowCount() {
        return sizeX;
    }

    public ModifiableSet getModifiableSet(int row) {
        return relation[row];
    }

    public void addSet(Set set) {
        int oldRowCount = getRowCount();
        setDimension(oldRowCount + 1, getColCount());
        getModifiableSet(oldRowCount).copy(set);
    }

    public Set getSet(int x) {
        return getModifiableSet(x);
    }


    public synchronized void removeCol(int col) {
        Assert.isTrue(0 <= col, "removeCol: col should be greater or equal to zero");
        Assert.isTrue(col < sizeY, "removeCol: col = " + col + "  should be less then relation column count");
        for (int j = sizeX; --j >= 0;) {
            relation[j].exclude(col);
        }
        --sizeY;
    }

    public synchronized void removeRow(int row) {
        Assert.isTrue(0 <= row, "removeRow: row should be greater or equal to zero");
        Assert.isTrue(row < sizeX, "removeRow: row should be less then relation row count");
        int lastElIndex = sizeX - 1;
        if (row != lastElIndex) {
            System.arraycopy(relation, row + 1, relation, row, lastElIndex - row);
        }
        relation[lastElIndex] = null;
        sizeX = lastElIndex;
    }

    public synchronized void setDimension(int rows, int cols) {
        if (rows < 0) {
            throw new IndexOutOfBoundsException("Dimension X of relation should be nonnegative");
        } // end of if ()
        if (cols < 0) {
            throw new IndexOutOfBoundsException("Dimension Y of relation should be nonnegative");
        } // end of if ()
        if (null == relation) {
            relation = new ModifiableSet[rows];
            for (int j = 0; j < rows; j++) {
                relation[j] = ContextFactoryRegistry.createSet(cols);
            }
        } else {
            if (sizeX < rows) {
                ModifiableSet[] temp = new ModifiableSet[rows];
                System.arraycopy(relation, 0, temp, 0, sizeX);
                relation = temp;
                for (int j = sizeX; j < rows; j++) {
                    relation[j] = ContextFactoryRegistry.createSet(cols);
                }
            }
        }
        int bound = Math.min(sizeX, rows);

        sizeX = rows;

        if (cols != sizeY) {
            for (int i = 0; i < bound; i++) {
                relation[i].resize(cols);
            }
        }
        sizeY = cols;
    }

    /**
     * @param x     x-coordinate
     * @param y     y-coordinate
     * @param value new value for cell with coordinates(x,y)
     */
    public void setRelationAt(int x, int y, boolean value) {
        synchronized (this) {
            Assert.isTrue(x < sizeX, "Dimension X of relation " + x + " should be less then sizeX=" + sizeX);
            // else indexOutOfBounds will be thrown from Java standart classes
        }
        if (value) {
            relation[x].put(y);
        } else {
            relation[x].remove(y);
        }
    }


    /**
     * Insert the method's description here.
     * Creation date: (04.08.01 7:12:40)
     *
     * @param obj java.lang.Object
     * @return boolean
     */
    public synchronized boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof BinaryRelation)) {
            return false;
        }
        BinaryRelation other = (BinaryRelation) obj;
        if (other.getColCount() != this.getColCount()) {
            return false;
        }
        if (other.getRowCount() != this.getRowCount()) {
            return false;
        }
        for (int i = getRowCount(); --i >= 0;) {
            for (int j = getColCount(); --j >= 0;) {
                if (getRelationAt(i, j) != other.getRelationAt(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }


    public int hashCode() {
        return 29 * sizeY + sizeX;
    }

    /**
     * Insert the method's description here.
     * Creation date: (04.08.01 8:39:27)
     */
    public String toString() {
        StringWriter sw = new StringWriter();
        BinaryRelationUtils.logRelation(this, new PrintWriter(sw));
        return sw.getBuffer().toString();
    }


}
