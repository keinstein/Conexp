package conexp.core.layout;

import conexp.core.LatticeElement;
import conexp.core.Lattice;

/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 */
public interface ILayerAssignmentFunction {
    interface ILayerAssignmentFunctionCallback{
        void layerForLatticeElement(LatticeElement latticeElement, int layer);
    }

    void calculateLayersForLattice(Lattice lattice, ILayerAssignmentFunctionCallback callback);
}
