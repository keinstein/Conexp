/*
 * User: Serhiy Yevtushenko
 * Date: Aug 13, 2002
 * Time: 1:32:15 PM
 */
package conexp.core.enumerators;

import conexp.core.Edge;
import conexp.core.EdgeIterator;
import conexp.core.Lattice;
import conexp.core.LatticeElement;

public class SimpleEdgeIterator implements EdgeIterator {
    protected Edge next;
    protected java.util.Iterator predEdgeIter;
    protected java.util.Iterator elemIter;

    public SimpleEdgeIterator(Lattice lat) {
        elemIter = lat.elements();
        predEdgeIter = null;
        next = findNextEdge();
    }


    /**
     * Creation date: (04.05.01 22:16:10)
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
                predEdgeIter = curr.predessors();
            }
        }
    }

    protected  boolean acceptElement(LatticeElement itemset){
        return true;
    }

    /**
     * Creation date: (04.05.01 21:32:23)
     * @return boolean
     */
    public boolean hasNextEdge() {
        return next != null;
    }

    /**
     * Creation date: (04.05.01 21:33:22)
     * @return conexp.core.Edge
     */
    public Edge nextEdge() {
        Edge ret = next;
        next = findNextEdge();
        return ret;
    }
}
