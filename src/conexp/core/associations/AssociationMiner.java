package conexp.core.associations;

import conexp.core.Context;
import conexp.core.DependencySet;
import conexp.core.ImplicationSet;
import conexp.core.Lattice;

/**
 * Insert the type's description here.
 * Creation date: (01.05.01 13:59:17)
 */
public interface AssociationMiner extends FrequentSetSupportSupplier{
    Lattice findFrequentItemsetsAndImplications(int minSupport);
    ImplicationSet getImplicationBase();
    void setContext(Context cxt);

    void findAssociations(DependencySet depSet, int minSupport, double minConfidence);
    void findAssociationsRuleCover(DependencySet cover, double minConfidence);

    int numberOfFrequentClosedItemsets();
    int numberOfEdges();
}