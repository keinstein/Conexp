/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.layout.chaindecomposition;

import conexp.util.GenericStrategy;

public interface ConceptPlacementStrategy extends GenericStrategy {
    double calcXCoord(double base, int currChain, int maxChain);

    double calcYCoord(double base, int currChain, int maxChain);
}
