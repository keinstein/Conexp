/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.core.compareutils;

import conexp.core.LatticeElement;


public class LatticeElementCompareInfo extends CompareInfo {
    protected DiffMap predEdgeCompare;
    protected DiffMap succEdgeCompate;

    LatticeElementCompareInfo(Object el, int t) {
        super(el, t);
    }

    protected boolean doCompareElements() {
        predEdgeCompare = new DiffMap(DefaultCompareInfoFactory.getInstance());
        final LatticeElement firstConcept = ((LatticeElement) one);
        final LatticeElement secondConcept = ((LatticeElement) two);
        boolean ret = predEdgeCompare.compareSets(new LatticeElementCollectionCompareSet(firstConcept.getPredecessors()),
                new LatticeElementCollectionCompareSet(secondConcept.getPredecessors()));
        succEdgeCompate = new DiffMap(DefaultCompareInfoFactory.getInstance());
        ret &= succEdgeCompate.compareSets(new LatticeElementCollectionCompareSet(firstConcept.getSuccessors()),
                new LatticeElementCollectionCompareSet(secondConcept.getSuccessors()));

        if (!ret) {
            makeInBothDifferent();
        }
        return ret;
    }

    protected void doDumpDifferencesForInBoth(java.io.PrintWriter writer) {
        util.Assert.isTrue(getType() == IN_BOTH_BUT_DIFFERENT);
        writer.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        writer.println("LatticeElement with desription " + one + " were different");
        writer.println("Differences in predecessors edges:");
        predEdgeCompare.dumpDifferences(writer);
        writer.println("---------------------------------------------------------");
        writer.println("Differences in successors edges");
        succEdgeCompate.dumpDifferences(writer);
        writer.println("end logging " + one);
        writer.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }
}
