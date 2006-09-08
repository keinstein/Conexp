/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.compareutils;

import conexp.core.LatticeElement;
import conexp.core.ModifiableSet;
import conexp.core.Set;
import util.Assert;

import java.io.PrintWriter;


public class LatticeElementCompareInfo extends CompareInfo {
    protected DiffMap predEdgeCompare;
    protected DiffMap succEdgeCompare;
    boolean predSame = true;
    boolean succSame = true;

    LatticeElementCompareInfo(Object key, Object object, int type) {
        super(key, object, type);
    }

    protected boolean doCompareElements() {
        predEdgeCompare = new DiffMap(DefaultCompareInfoFactory.getInstance());
        final LatticeElement firstConcept = (LatticeElement) one;
        final LatticeElement secondConcept = (LatticeElement) two;
        boolean ret = predSame = predEdgeCompare.compareSets(new LatticeElementCollectionCompareSet(firstConcept.getPredecessors()),
                new LatticeElementCollectionCompareSet(secondConcept.getPredecessors()));
        succEdgeCompare = new DiffMap(DefaultCompareInfoFactory.getInstance());
        ret &= succSame = succEdgeCompare.compareSets(new LatticeElementCollectionCompareSet(firstConcept.getSuccessors()),
                new LatticeElementCollectionCompareSet(secondConcept.getSuccessors()));

        if (!ret) {
            makeInBothDifferent();
        }
        return ret;
    }

    protected void doDumpDifferencesForInBoth(PrintWriter writer) {
        Assert.isTrue(getType() == IN_BOTH_BUT_DIFFERENT);
        writer.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        writer.println("LatticeElement with desription " + one + " were different");
        writer.println("one :" + one);
        writer.println("two :" + two);
        final Set oneObjects = ((LatticeElement) one).getObjects();
        final Set twoObjects = ((LatticeElement) two).getObjects();
        ModifiableSet oneMinusTwo = oneObjects.makeModifiableSetCopy();
        oneMinusTwo.andNot(twoObjects);
        ModifiableSet twoMinusOne = twoObjects.makeModifiableSetCopy();
        twoMinusOne.andNot(oneObjects);
        oneMinusTwo.or(twoMinusOne);
        if (!oneMinusTwo.isEmpty()) {
            writer.println("following objects are different " + oneMinusTwo);
        }

        if (!predSame) {
            writer.println("---------------------------------------------------------");
            writer.println("Differences in predecessors edges:");
            predEdgeCompare.dumpDifferences(writer);
        }
        if (!succSame) {
            writer.println("---------------------------------------------------------");
            writer.println("Differences in successors edges");
            succEdgeCompare.dumpDifferences(writer);
        }
        writer.println("---------------------------------------------------------");
        writer.println("end logging");
        writer.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }
}
