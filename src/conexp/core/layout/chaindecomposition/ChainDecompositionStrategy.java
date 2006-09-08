/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.layout.chaindecomposition;

import conexp.core.BinaryRelation;
import conexp.core.ExtendedContextEditingInterface;
import conexp.core.LatticeElement;
import conexp.core.Set;
import conexp.util.GenericStrategy;

public interface ChainDecompositionStrategy extends GenericStrategy {
    /**
     * Insert the method's description here.
     * Creation date: (09.03.01 15:46:10)
     *
     * @return conexp.core.BinaryRelation
     */
    BinaryRelation computeEntitiesOrder();

    /**
     * Insert the method's description here.
     * Creation date: (10.03.01 1:25:31)
     *
     * @param el       conexp.core.LatticeElement
     * @param vectorsX double[]
     * @param vectorsY double[]
     * @param pt       conexp.core.layout.Point2D
     */
    Set conceptRepresentation(LatticeElement el);

    /**
     * Insert the method's description here.
     * Creation date: (09.03.01 15:41:59)
     *
     * @return int
     */
    int getEntitiesCount();

    /**
     * Insert the method's description here.
     * Creation date: (09.03.01 19:00:29)
     *
     * @return int
     */
    int getYSign();

    /**
     * Insert the method's description here.
     * Creation date: (09.03.01 20:26:22)
     *
     * @param v int
     * @return boolean
     */
    boolean isEntityIrreducible(int v);

    void setContext(ExtendedContextEditingInterface cxt);
}
