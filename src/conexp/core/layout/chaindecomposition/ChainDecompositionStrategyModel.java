/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.core.layout.chaindecomposition;

public class ChainDecompositionStrategyModel extends conexp.frontend.latticeeditor.DrawParamsIndependentStrategyModel {
    public ChainDecompositionStrategyModel() {
        super();
    }


    /**
     * Insert the method's description here.
     * Creation date: (14.06.01 20:39:48)
     * @return java.lang.String[][]
     */
    public java.lang.String[][] getCreateInfo() {
        return new String[][]{{"Attribute-based", "conexp.core.layout.chaindecomposition.AttributtesDecompositionStrategy"},
                              {"Object-based", "conexp.core.layout.chaindecomposition.ObjectsDecompositionStrategy"}
        };
    }
}
