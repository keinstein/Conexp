/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.enumerators;

import conexp.core.Edge;
import conexp.core.EdgeIterator;
import conexp.core.Lattice;
import conexp.core.LatticeElement;

import java.util.Iterator;

public class SimpleEdgeIterator implements EdgeIterator {
    protected Edge next;
    protected Iterator predEdgeIter;
    protected Iterator elemIter;

    public SimpleEdgeIterator(Lattice lat) {
        elemIter = lat.elements();
        predEdgeIter = null;
        next = findNextEdge();
    }


    /**
     * Creation date: (04.05.01 22:16:10)
     *
     * @return conexp.core.Edge
     */
    protected Edge findNextEdge() {
        while (true) {
            if (null != predEdgeIter) {
                while (predEdgeIter.hasNext()) {
                    Edge e = (Edge) predEdgeIter.next();
                    final LatticeElement start = e.getStart();
                    if (acceptElement(start)) {
                        return e;
                    }
                }
                predEdgeIter = null;
            }
            if (!elemIter.hasNext()) {
                return null;
            }
            LatticeElement curr = (LatticeElement) elemIter.next();
            if (acceptElement(curr)) {
                predEdgeIter = curr.predessorsEdges();
            }
        }
    }

    protected boolean acceptElement(LatticeElement itemset) {
        return true;
    }

    /**
     * Creation date: (04.05.01 21:32:23)
     *
     * @return boolean
     */
    public boolean hasNextEdge() {
        return next != null;
    }

    /**
     * Creation date: (04.05.01 21:33:22)
     *
     * @return conexp.core.Edge
     */
    public Edge nextEdge() {
        Edge ret = next;
        next = findNextEdge();
        return ret;
    }
}
