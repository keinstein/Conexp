/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.latticeeditor;

import conexp.util.GenericStrategy;

public interface DimensionCalcStrategy extends GenericStrategy {
    void initCalc();

    void setConceptSet(conexp.core.ConceptsCollection conceptSet);
}
