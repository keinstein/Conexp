/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.core.layout.chaindecomposition;




public class ConceptPlacementStrategyModel extends conexp.frontend.latticeeditor.DrawParamsIndependentStrategyModel {
    public ConceptPlacementStrategyModel() {
    }

    public java.lang.String[][] getCreateInfo() {
        return new String[][]{{"Exponentional", "ExponentialPlacementStrategy", "conexp.core.layout.chaindecomposition.ExponentialPlacementStrategy"},
                              {"Straight", "StraightPlacementStrategy", "conexp.core.layout.chaindecomposition.StraightPlacementStrategy"},
                              {"Angular", "AngularPlacementStrategy", "conexp.core.layout.chaindecomposition.AngularPlacementStrategy"}
        };

    }
}
