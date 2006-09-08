/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.associations;

import conexp.core.Context;
import conexp.core.DependencySet;
import conexp.core.ImplicationSet;
import conexp.core.Lattice;


public interface AssociationMiner extends FrequentSetSupportSupplier {
    Lattice findFrequentItemsetsAndImplications(int minSupport);

    ImplicationSet getImplicationBase();

    void setContext(Context cxt);

    void findAssociations(DependencySet depSet, int minSupport, double minConfidence);

    void findAssociationsRuleCover(DependencySet cover, double minConfidence);

    int numberOfFrequentClosedItemsets();

    int numberOfEdges();
}
