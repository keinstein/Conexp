/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.latticeeditor;

import conexp.util.GenericStrategy;
import conexp.core.ConceptsCollection;

public interface DimensionCalcStrategy extends GenericStrategy {
    void initCalc();

    void setConceptSet(ConceptsCollection conceptSet);
}
