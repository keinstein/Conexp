package conexp.core.enumcallbacks;

import conexp.core.*;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Insert the type's description here.
 * Creation date: (10.07.01 7:02:32)
 * @author
 */
public class NextClosedSetLatticeBuilderCallback extends ConceptSetCallback {

    protected conexp.core.BinaryRelation rel;

    /*****************************************
     * this function is called at the end of
     * calculation of conexp set
     *****************************************/
    public void finishCalc() {
        Lattice lat = (Lattice) conceptSet;
        lat.setOne(lat.elementAt(0));
        lat.setZero(lat.elementAt(lat.conceptsCount() - 1));

        LinkedList upperCovers = new LinkedList();
        ModifiableSet temp = ContextFactoryRegistry.createSet(rel.getColCount());
        for (int i = lat.conceptsCount(); --i >= 1;) {
            LatticeElement el = lat.elementAt(i);
            for (int j = rel.getRowCount(); --j >= 0;) {
                if (!el.getObjects().in(j)) {
                    temp.copy(el.getAttribs());
                    temp.and(rel.getSet(j));
                    if (temp.isSubsetOf(el.getAttribs())) {
                        upperCovers.add(temp.clone());
                    }
                }
            }
            while (!upperCovers.isEmpty()) {
                Set curr = (Set) upperCovers.removeFirst();
                outer : {
                    Iterator iter = upperCovers.iterator();
                    while (iter.hasNext()) {
                        Set otherSet = (Set) iter.next();
                        if (curr.isSupersetOf(otherSet)) {
                            iter.remove();
                        } else if (curr.isSubsetOf(otherSet)) {
                            break outer;
                        }
                    }
                    LatticeElement found = findMinConceptWithIntentAndAttrib(curr, i);
                    el.addSucc(found);
                } //outer
            }//		while (!upperCovers.isEmpty()) {

        }
    }


    public NextClosedSetLatticeBuilderCallback(conexp.core.Lattice lat) {
        super(lat);
    }


    /**
     * Insert the method's description here.
     * Creation date: (10.07.01 8:01:10)
     * @return conexp.core.LatticeElement
     * @param intent conexp.core.Set
     */
    public LatticeElement findMinConceptWithIntentAndAttrib(Set intent, int endPos) {

        int low = 0;
        int high = endPos - 1;

        while (low <= high) {
            int mid = (low + high) / 2;

            ItemSet midVal = conceptSet.conceptAt(mid);

            int cmp = midVal.getAttribs().lexCompareGanter(intent);

            if (cmp < 0) {
                low = mid + 1;
            } else if (cmp > 0) {
                high = mid - 1;
            } else {
                return (LatticeElement) midVal;
            }
        }
        util.Assert.isTrue(false);
        return null;
    }


    /**
     * Insert the method's description here.
     * Creation date: (14.07.01 20:05:23)
     * @param rel conexp.core.BinaryRelation
     */
    public void setRelation(conexp.core.BinaryRelation rel) {
        this.rel = rel;
    }
}