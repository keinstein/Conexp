package conexp.core.layout.chaindecomposition;

import conexp.core.BinaryRelationUtils;

/**
 * Insert the type's description here.
 * Creation date: (09.03.01 19:10:14)
 * @author
 */
public class AttributtesDecompositionStrategy implements ChainDecompositionStrategy {
    private conexp.core.ExtendedContextEditingInterface cxt;

    /**
     * AttributtesDecompositionStrategy constructor comment.
     */
    public AttributtesDecompositionStrategy() {
        super();
    }

    /**
     * Insert the method's description here.
     * Creation date: (09.03.01 19:10:47)
     * @return conexp.core.BinaryRelation
     */
    public conexp.core.BinaryRelation computeEntitiesOrder() {
        return BinaryRelationUtils.calcAttributesOrder(cxt.getRelation());
    }

    /**
     * Insert the method's description here.
     * Creation date: (10.03.01 1:33:55)
     * @param el conexp.core.LatticeElement
     * @param vectorsX double[]
     * @param vectorsY double[]
     * @param pt conexp.core.layout.Point2D
     */
    public conexp.core.Set conceptRepresentation(conexp.core.LatticeElement el) {
        return el.getAttribs();
    }

    /**
     * Insert the method's description here.
     * Creation date: (09.03.01 19:10:47)
     * @return int
     */
    public int getEntitiesCount() {
        return cxt.getAttributeCount();
    }

    /**
     * Insert the method's description here.
     * Creation date: (09.03.01 19:10:47)
     * @return int
     */
    public int getYSign() {
        return 1;
    }

    /**
     * Insert the method's description here.
     * Creation date: (09.03.01 20:26:38)
     * @return boolean
     * @param v int
     */
    public boolean isEntityIrreducible(int v) {
        for (int i = cxt.getObjectCount(); --i >= 0;) {
            if (cxt.hasUpArrow(i, v)) {
                return true;
            }
        }
        return false;
    }

    /**
     * setContext method comment.
     */
    public void setContext(conexp.core.ExtendedContextEditingInterface cxt) {
        this.cxt = cxt;
    }

    public String toString() {
        return "AttributtesDecompositionStrategy";
    }
}