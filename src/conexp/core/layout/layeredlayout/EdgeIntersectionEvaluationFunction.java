package conexp.core.layout.layeredlayout;

import conexp.core.Lattice;
import conexp.core.layout.ConceptCoordinateMapper;

/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 * Date: 11/2/2004
 * Time: 23:20:35
 */

public class EdgeIntersectionEvaluationFunction extends LatticeBasedEvaluationFunctionBase {

    public EdgeIntersectionEvaluationFunction(Lattice lattice, ConceptCoordinateMapper layeredLayoter) {
        super(lattice, layeredLayoter);
    }



    //the bigger value of evaluation function is better
    public double getEvaluationForLattice() {
            //for each edge a
            //for each edge b!=a
                //if ( a intersects b)
                    //add count

        return 0;
    }
}
