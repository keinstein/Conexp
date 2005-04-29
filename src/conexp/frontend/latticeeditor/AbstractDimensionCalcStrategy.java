/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.latticeeditor;

import conexp.core.ConceptsCollection;


public abstract class AbstractDimensionCalcStrategy implements DimensionCalcStrategy {
    public AbstractDimensionCalcStrategy() {
        super();
    }

    public void initCalc() {
    }


    public void setConceptSet(ConceptsCollection conceptSet) {
    }
}
