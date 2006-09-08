/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor;

import conexp.core.ConceptsCollection;
import conexp.util.GenericStrategy;

public interface DimensionCalcStrategy extends GenericStrategy {
    void initCalc();

    void setConceptSet(ConceptsCollection conceptSet);
}
