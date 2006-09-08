/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.core;

import java.text.MessageFormat;


public class LatticeStatistics {
    final Lattice lattice;

    public LatticeStatistics(Lattice lattice) {
        this.lattice = lattice;
    }

    public String getDescriptionString() {
        return MessageFormat.format("Concept count {0} \n" +
                "Edge count {1}\nLattice height {2} \nLattice width estimation [{3}, {4}]",
                new Object[]{new Integer(lattice.conceptsCount()),
                        new Integer(lattice.edgeCount()),
                        new Integer(lattice.getHeight()),
                        new Integer(LatticeAlgorithms.latticeWidthLowerBound(lattice)), new Integer(LatticeAlgorithms.latticeWidthUpperBound(lattice))});
    }
}
