/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.layout.chaindecomposition;

import conexp.util.gui.strategymodel.AbstractNonGrowingStrategyModel;

public class ChainDecompositionStrategyModel extends AbstractNonGrowingStrategyModel {
    public ChainDecompositionStrategyModel() {
        super();
    }


    public String[][] getCreateInfo() {
        return new String[][]{{"Attribute-based", "AttributtesDecompositionStrategy", "conexp.core.layout.chaindecomposition.AttributtesDecompositionStrategy"},
                {"Object-based", "ObjectsDecompositionStrategy", "conexp.core.layout.chaindecomposition.ObjectsDecompositionStrategy"}
        };
    }
}
