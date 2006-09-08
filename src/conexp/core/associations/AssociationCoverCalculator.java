/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.associations;

import conexp.core.ContextFactoryRegistry;
import conexp.core.Dependency;
import conexp.core.DependencySet;
import conexp.core.ItemSet;
import conexp.core.Lattice;
import conexp.core.LatticeElement;
import conexp.core.ModifiableSet;
import conexp.core.enumerators.ConceptFilterIterator;
import util.collection.CollectionFactory;

import java.util.LinkedList;

public class AssociationCoverCalculator {
    private int[] predCount;

    public void findAssociationsRuleCover(DependencySet result, Lattice frequentItemsetCollection,
                                          double confidence) {

        //this is version of cover algorithm, that is specially using the fact, that we can have ordered
        //diagram of the set of frequent itemsets
        //so, we are performing factually topological search.
        //this search can be optimized further in several directions:

        LinkedList queue = calculatePredTableAndInitQueueWithMaximalFrequentItemsets(frequentItemsetCollection);

        //one - we can store set of minimal noncomparable itemsets for the last parent of current itemset,
        //and then prune it according to current itemset and use it as a starting points for other itemset minimal
        //itemset search


        LatticeElement topOfSearchSpace = frequentItemsetCollection.getOne();
        result.clear();
        while (!queue.isEmpty()) {
            LatticeElement current = (LatticeElement) queue.removeFirst();
            ItemSet parentHavingMaximalSupportThatStillCreatesAssociationRule =
                    findParentWithMaximalSupportAndMinimalLengthCreatingAssociation(current, confidence);
            if (thereAreNoRuleThatCoversCurrent(result, parentHavingMaximalSupportThatStillCreatesAssociationRule, current))
            {
                result.addDependency(AssociationRule.makeFromItemsets(parentHavingMaximalSupportThatStillCreatesAssociationRule,
                        current));
            }

            if (parentHavingMaximalSupportThatStillCreatesAssociationRule != topOfSearchSpace) {
                for (int i = 0; i < current.getSuccCount(); i++) {
                    LatticeElement parent = current.getSucc(i);
                    if (--predCount[parent.getIndex()] == 0) {
                        queue.add(parent);
                    }
                }
            }

        }
    }

    public LinkedList calculatePredTableAndInitQueueWithMaximalFrequentItemsets(Lattice frequentItemsetCollection) {
        predCount = new int[frequentItemsetCollection.conceptsCount()];
        final LinkedList queue = CollectionFactory.createFastInsertDeleteList();
        frequentItemsetCollection.forEach(new Lattice.LatticeElementVisitor() {
            public void visitNode(LatticeElement node) {
                final int currNodePredCount = node.getPredCount();
                predCount[node.getIndex()] = currNodePredCount;
                if (0 == currNodePredCount) {
                    queue.add(node);
                }
            }
        });
        return queue;
    }

    public static boolean thereAreNoRuleThatCoversCurrent(DependencySet cover, ItemSet parentHavingMaximalSupportThatStillCreatesAssociationRule, LatticeElement current) {
        ModifiableSet dependencyAttributeSet = ContextFactoryRegistry.createSet(current.getAttribs().size());
        final int newPremiseObjectCount = parentHavingMaximalSupportThatStillCreatesAssociationRule.getObjCnt();

        final int count = cover.getSize();
        for (int i = 0; i < count; i++) {
            Dependency dependency = cover.getDependency(i);
            dependencyAttributeSet.copy(dependency.getPremise());
            dependencyAttributeSet.or(dependency.getConclusion());

            if (current.getAttribs().isSubsetOf(dependencyAttributeSet)) {
                if (newPremiseObjectCount <= dependency.getPremiseSupport()) {
                    return false;
                }
            }
        }

        return true;
    }

    public static LatticeElement findParentWithMaximalSupportAndMinimalLengthCreatingAssociation(LatticeElement current, double confidence) {
        ConceptFilterIterator filterIterator = new ConceptFilterIterator(current);
        LatticeElement ret = null;
        double currentSupport = current.getObjCnt();
        while (filterIterator.hasNext()) {
            LatticeElement next = filterIterator.nextConcept();
            if (ret == null) {
                ret = next;
            } else {
                final int nextSupport = next.getObjCnt();
                if (currentSupport >= nextSupport * confidence) {
                    final int retSupport = ret.getObjCnt();
                    if (nextSupport > retSupport) {
                        ret = next;
                    } else if (nextSupport == retSupport) {
                        if (next.getAttribs().elementCount() < ret.getAttribs().elementCount()) {
                            ret = next;
                        }
                    }
                }
            }
        }
        return ret;
    }
}
