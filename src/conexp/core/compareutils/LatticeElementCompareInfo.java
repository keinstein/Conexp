/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.core.compareutils;

import conexp.core.LatticeElement;


public class LatticeElementCompareInfo extends CompareInfo {
    protected DiffMap edgeCompare;

    LatticeElementCompareInfo(Object el, int t) {
        super(el, t);
    }

    protected boolean doCompareElements() {
        edgeCompare = new DiffMap(DefaultCompareInfoFactory.getInstance());
        boolean ret = edgeCompare.compareSets(new LatticeElementPredCompareSet((LatticeElement) one),
                new LatticeElementPredCompareSet((LatticeElement) two));
        if (!ret) {
            makeInBothDifferent();
        }
        return ret;
    }

    protected void doDumpDifferencesForInBoth(java.io.PrintWriter writer) {
        util.Assert.isTrue(getType() == IN_BOTH_BUT_DIFFERENT);
        writer.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        writer.println("LatticeElement with desription " + one + " were different");
        edgeCompare.dumpDifferences(writer);
        writer.println("end logging " + one);
        writer.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }
}
