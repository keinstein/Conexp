/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.layout.chaindecomposition;

import conexp.core.BinaryRelation;
import conexp.core.ContextFactoryRegistry;
import conexp.core.ExtendedContextEditingInterface;
import conexp.core.LatticeElement;
import conexp.core.ModifiableBinaryRelation;
import conexp.core.Set;


public class ObjectsDecompositionStrategy implements ChainDecompositionStrategy {
    private ExtendedContextEditingInterface cxt;

    /**
     * Insert the method's description here.
     * Creation date: (09.03.01 19:08:06)
     *
     * @return conexp.core.BinaryRelation
     */
    public BinaryRelation computeEntitiesOrder() {
        final int size = getEntitiesCount();
        BinaryRelation rel = cxt.getRelation();
        ModifiableBinaryRelation ret = ContextFactoryRegistry.createRelation(size, size);
        for (int i = 0; i < size; i++) {
            Set curr = rel.getSet(i);
            for (int j = 0; j < size; j++) {
                if (curr.isSubsetOf(rel.getSet(j))) {
                    ret.setRelationAt(i, j, true);
                }
            }
        }
        return ret;
    }

    /**
     * Insert the method's description here.
     * Creation date: (10.03.01 1:34:34)
     *
     * @param el       conexp.core.LatticeElement
     * @param vectorsX double[]
     * @param vectorsY double[]
     * @param pt       conexp.core.layout.Point2D
     */
    public Set conceptRepresentation(LatticeElement el) {
        return el.getObjects();
    }

    /**
     * Insert the method's description here.
     * Creation date: (09.03.01 19:08:06)
     *
     * @return int
     */
    public int getEntitiesCount() {
        return cxt.getObjectCount();
    }

    /**
     * Insert the method's description here.
     * Creation date: (09.03.01 19:08:06)
     *
     * @return int
     */
    public int getYSign() {
        return -1;
    }

    /**
     * Insert the method's description here.
     * Creation date: (09.03.01 20:26:49)
     *
     * @param v int
     * @return boolean
     */
    public boolean isEntityIrreducible(int v) {
        for (int i = cxt.getAttributeCount(); --i >= 0;) {
            if (cxt.hasDownArrow(v, i)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Insert the method's description here.
     * Creation date: (10.03.01 10:23:43)
     *
     * @param newCxt conexp.core.Context
     */
    public void setContext(ExtendedContextEditingInterface newCxt) {
        cxt = newCxt;
    }

    /**
     * Insert the method's description here.
     * Creation date: (23.04.01 23:39:00)
     */
    public String toString() {
        return "ObjectDecompositionStrategy";
    }
}
