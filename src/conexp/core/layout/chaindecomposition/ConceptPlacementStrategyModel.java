/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.core.layout.chaindecomposition;

import conexp.frontend.latticeeditor.DrawParameters;


public class ConceptPlacementStrategyModel extends conexp.frontend.latticeeditor.DrawParamsIndependentStrategyModel {
    /**
     * ChainDecompositionStrategyModel constructor comment.
     * @param opt conexp.frontend.latticeeditor.LatticePainterDrawParams
     */
    public ConceptPlacementStrategyModel() {
    }


    /**
     * Insert the method's description here.
     * Creation date: (30.03.01 0:45:02)
     */
    protected void createStrategies(DrawParameters opt) {
        allocateStrategies(3);
        setStrategy(0, "Exponentional", new ExponentialPlacementStrategy());
        setStrategy(1, "Straight", new StraightPlacementStrategy());
        setStrategy(2, "Angular", new AngularPlacementStrategy());
    }


    /**
     * Insert the method's description here.
     * Creation date: (14.06.01 21:02:43)
     * @return java.lang.String[][]
     */
    public java.lang.String[][] getCreateInfo() {
        return new String[][]{{"Exponentional", "conexp.core.layout.chaindecomposition.ExponentialPlacementStrategy"},
                              {"Straight", "conexp.core.layout.chaindecomposition.StraightPlacementStrategy"},
                              {"Angular", "conexp.core.layout.chaindecomposition.AngularPlacementStrategy"}
        };

    }
}
