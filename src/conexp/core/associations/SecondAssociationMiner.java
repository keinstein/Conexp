/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.core.associations;

import conexp.core.DependencySet;
import conexp.core.FCAEngineRegistry;
import conexp.core.ImplicationSet;
import conexp.core.Lattice;
import conexp.core.calculationstrategies.LatticeImplicationCalculator;

import java.util.ArrayList;


public class SecondAssociationMiner extends BaseAssociationMiner {

    public SecondAssociationMiner() {
        super();
    }

    public void findAssociations(DependencySet depSet, int minSupport, double minConfidence) {
        util.Assert.isTrue(cxt != null);
        depSet.clear();
        findFrequentItemsetsAndImplications(minSupport);
        long exactTime = System.currentTimeMillis();

        ArrayList frequentEdges = findFrequentEdgesSortedByConfidence(frequentItemsetLattice, minConfidence, minSupport);
        long sortTime = System.currentTimeMillis();
        findApproximateBaseByCruscal(depSet, frequentEdges, frequentItemsetLattice.conceptsCount());

        long approximateTime = System.currentTimeMillis();
        System.out.println("approximate dependencies:" + (approximateTime - exactTime));
        System.out.println("Time to sort edges:" + (sortTime - exactTime));
        System.out.println("Time to find base:" + (approximateTime - sortTime));
        final int exactCount = validImplications.getSize();
        for (int i = 0; i < exactCount; i++) {
            depSet.addDependency(validImplications.getDependency(i));
        }
    }


    public Lattice findFrequentItemsetsAndImplications(int minSupport) {
        long startTime = System.currentTimeMillis();
        findFrequentItemsetsLattice(minSupport);
        long latticeTime = System.currentTimeMillis();
        System.out.println("Time for building lattice:" + (latticeTime - startTime));

        validImplications = new ImplicationSet(cxt);
        LatticeImplicationCalculator calc = new LatticeImplicationCalculator();
        calc.setLattice(frequentItemsetLattice);
        calc.setImplications(validImplications);
        calc.calcImplications();
        long exactTime = System.currentTimeMillis();
        System.out.println("Time for finding validImplications dependencies:" + (exactTime - latticeTime));

        return frequentItemsetLattice;
    }

    public Lattice findFrequentItemsetsLattice(int minSupport) {
        frequentItemsetLattice = FCAEngineRegistry.buildIcebergLattice(cxt, minSupport);
        return frequentItemsetLattice;
    }


}
