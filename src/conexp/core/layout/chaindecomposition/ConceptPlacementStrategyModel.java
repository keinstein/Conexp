/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.layout.chaindecomposition;

import conexp.util.gui.strategymodel.AbstractNonGrowingStrategyModel;


public class ConceptPlacementStrategyModel extends AbstractNonGrowingStrategyModel {
    public ConceptPlacementStrategyModel() {
    }

    public String[][] getCreateInfo() {
        return new String[][]{{"Exponentional", "ExponentialPlacementStrategy", "conexp.core.layout.chaindecomposition.ExponentialPlacementStrategy"},
                {"Straight", "StraightPlacementStrategy", "conexp.core.layout.chaindecomposition.StraightPlacementStrategy"},
                {"Angular", "AngularPlacementStrategy", "conexp.core.layout.chaindecomposition.AngularPlacementStrategy"}
        };

    }
}
