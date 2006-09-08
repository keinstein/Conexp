/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.core.layout;

import conexp.core.Lattice;
import conexp.core.LatticeElement;
import util.Assert;


public class HeightInLatticeLayerAssignmentFunction implements ILayerAssignmentFunction {
    public void calculateLayersForLattice(Lattice lattice, final ILayerAssignmentFunction.ILayerAssignmentFunctionCallback callback) {
        Assert.isTrue(lattice.getHeight() >= 0);
        lattice.doTopSort(new Lattice.DefaultTopSortBlock() {
            public void assignTopSortNumberToElement(LatticeElement currentElement, int topSortNumber) {
                callback.layerForLatticeElement(currentElement, currentElement.getHeight());
            }
        });
    }

    private HeightInLatticeLayerAssignmentFunction() {
    }

    static final ILayerAssignmentFunction g_Instance = new HeightInLatticeLayerAssignmentFunction();

    public static ILayerAssignmentFunction getInstance() {
        return g_Instance;
    }
}
