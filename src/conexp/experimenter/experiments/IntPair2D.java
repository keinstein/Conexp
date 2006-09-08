/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.experimenter.experiments;


public class IntPair2D implements Comparable {
    final int x;
    final int y;

    public IntPair2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof IntPair2D)) {
            return false;
        }

        final IntPair2D intPair2D = (IntPair2D) obj;

        if (x != intPair2D.x) {
            return false;
        }
        if (y != intPair2D.y) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int result;
        result = x;
        result = 29 * result + y;
        return result;
    }

    public int compareTo(Object o) {
        IntPair2D other = (IntPair2D) o;
        int diff = this.x - other.x;
        if (diff != 0) {
            return diff;
        }
        return this.y - other.y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
