/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.core.layout.chaindecomposition;

import conexp.frontend.latticeeditor.DrawParamsIndependentStrategyModel;

public class ChainDecompositionStrategyModel extends DrawParamsIndependentStrategyModel {
    public ChainDecompositionStrategyModel() {
        super();
    }


    public java.lang.String[][] getCreateInfo() {
        return new String[][]{{"Attribute-based", "AttributtesDecompositionStrategy","conexp.core.layout.chaindecomposition.AttributtesDecompositionStrategy"},
                              {"Object-based", "ObjectsDecompositionStrategy", "conexp.core.layout.chaindecomposition.ObjectsDecompositionStrategy"}
        };
    }
}
