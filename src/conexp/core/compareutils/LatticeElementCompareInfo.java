package conexp.core.compareutils;

import conexp.core.LatticeElement;

/**
 * Insert the type's description here.
 * Creation date: (13.07.01 17:12:07)
 * @author Serhiy Yevtushenko
 */
public class LatticeElementCompareInfo extends CompareInfo {
    protected DiffMap edgeCompare;

    LatticeElementCompareInfo(Object el, int t) {
        super(el, t);
    }

    protected boolean doCompareElements() {
        edgeCompare = new DiffMap(new DefaultCompareInfoFactory());
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