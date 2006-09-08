/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.core.layout;

import conexp.core.Lattice;
import conexp.core.LatticeElement;


public interface ILayerAssignmentFunction {
    interface ILayerAssignmentFunctionCallback {
        void layerForLatticeElement(LatticeElement latticeElement, int layer);
    }

    void calculateLayersForLattice(Lattice lattice, ILayerAssignmentFunctionCallback callback);
}
