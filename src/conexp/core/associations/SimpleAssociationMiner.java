/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.associations;

import conexp.core.Dependency;
import conexp.core.DependencySet;
import conexp.core.FCAEngineRegistry;
import conexp.core.ImplicationCalcStrategy;
import conexp.core.ImplicationSet;
import conexp.core.Lattice;
import conexp.core.calculationstrategies.DepthSearchCalculator;
import conexp.core.calculationstrategies.NextClosedSetImplicationCalculator;
import util.Assert;

import java.util.List;


public class SimpleAssociationMiner extends BaseAssociationMiner {

    public SimpleAssociationMiner() {
        super();
    }

    public void findAssociations(DependencySet depSet, int minSupport, double minConfidence) {
        Assert.isTrue(cxt != null);
        depSet.clear();
//        long startTime = System.currentTimeMillis();
        findExactDependencies(depSet, minSupport);
//        long exactTime = System.currentTimeMillis();
        findApproximateDependencies(depSet, minSupport, minConfidence);
/*
        long approximateTime = System.currentTimeMillis();
        System.out.println("Time for finding exact dependencies:" + (exactTime - startTime));
        System.out.println("approximate dependencies:" + (approximateTime - exactTime));
*/
    }

    private void findApproximateDependencies(DependencySet ret, int minSupport, double minConfidence) {
        frequentItemsetLattice = findFrequentItemsetsAndImplications(minSupport);
        List frequentEdges = findFrequentEdgesSortedByConfidence(frequentItemsetLattice, minConfidence, minSupport);
        findApproximateBaseByCruscal(ret, frequentEdges, frequentItemsetLattice.conceptsCount());
    }

    /**
     * Insert the method's description here.
     * Creation date: (09.05.01 10:12:34)
     */
    public Lattice findFrequentItemsetsAndImplications(int minSupport) {
        DepthSearchCalculator latticeCalc = new DepthSearchCalculator();
        Lattice lat = FCAEngineRegistry.makeLatticeForContext(cxt);
        latticeCalc.setRelation(cxt.getRelation());
        latticeCalc.setLattice(lat);
        latticeCalc.buildLattice();
        return lat;
    }

    /**
     * Insert the method's description here.
     * Creation date: (04.05.01 23:59:03)
     *
     * @param minSupport int
     */
    private void findExactDependencies(DependencySet ret, int minSupport) {
        ImplicationCalcStrategy implCalc = new NextClosedSetImplicationCalculator();
        implCalc.setRelation(cxt.getRelation());
        validImplications = new ImplicationSet(cxt);
        implCalc.setImplications(validImplications);
        implCalc.calcImplications();
        selectDependenciesWithGreaterSupport(ret, validImplications, minSupport);
    }

    /**
     * Insert the method's description here.
     * Creation date: (09.06.01 20:33:31)
     *
     * @param minSupport int
     */
    private static void selectDependenciesWithGreaterSupport(DependencySet ret, DependencySet implSet, int minSupport) {
        for (int i = implSet.getSize(); --i >= 0;) {
            Dependency dep = implSet.getDependency(i);
            if (dep.getRuleSupport() >= minSupport) {
                ret.addDependency(dep);
            }
        }
    }


}
