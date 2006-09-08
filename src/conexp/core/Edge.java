/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core;


import util.Assert;

public class Edge {
//------------------------------------------------------------
    private LatticeElement start;
//------------------------------------------------------------
    private LatticeElement end;
//------------------------------------------------------------

    public Edge(LatticeElement _start, LatticeElement _end) {
        super();
        Assert.isTrue(null != _start, "Start element in edge can't be null");
        Assert.isTrue(null != _end, "End element in edge can't be null");
        start = _start;
        end = _end;
    }

    /**
     * Insert the method's description here.
     * Creation date: (01.12.00 3:06:17)
     *
     * @param el conexp.core.LatticeElement
     * @return boolean
     */
    public boolean contains(final LatticeElement el) {
        return start == el || end == el;
    }
//------------------------------------------------------------

    public LatticeElement getEnd() {
        return end;
    }
//------------------------------------------------------------

    public int getLength() {
        return end.getHeight() - start.getHeight();
    }
//------------------------------------------------------------

    public LatticeElement getStart() {
        return start;
    }

    /**
     * Insert the method's description here.
     * Creation date: (04.05.01 22:53:52)
     *
     * @param obj java.lang.Object
     * @return boolean
     */
    public boolean equals(Object obj) {
        if (null == obj ||
                !(obj instanceof Edge)) {
            return false;
        }
        Edge e = (Edge) obj;
        return start.equals(e.getStart()) && end.equals(e.getEnd());
    }


    /**
     * Insert the method's description here.
     * Creation date: (05.05.01 0:11:28)
     *
     * @return double
     */
    public double getConfidence() {
        return (double) start.getObjCnt() / end.getObjCnt();
    }


    /**
     * Insert the method's description here.
     * Creation date: (04.05.01 23:10:44)
     *
     * @return int
     */
    public int hashCode() {
        return start.hashCode() ^ end.hashCode();
    }


    /**
     * Insert the method's description here.
     * Creation date: (05.05.01 0:13:48)
     *
     * @return boolean
     */
    public boolean isValid() {
        if (null == start) {
            return false;
        }
        if (null == end) {
            return false;
        }
        if (start.getObjCnt() >= end.getObjCnt()) {
            return false;
        }
        //util.Assert.isTrue(getLength()>=1, "length should be greater or equals to one");
        return true;
    }


    /**
     * Insert the method's description here.
     * Creation date: (04.05.01 23:14:35)
     *
     * @return java.lang.String
     */
    public String toString() {
        return "Edge[ start=[" + start + "] end=[" + end + "] ]";
    }
}
