package conexp.core.compareutils;

import conexp.core.LatticeElement;

/**
 * Insert the type's description here.
 * Creation date: (13.07.01 19:17:34)
 * @author
 */
public class LatticeElementPredCompareSet implements ICompareSet {
    protected final LatticeElement el;

    /**
     * Insert the method's description here.
     * Creation date: (13.07.01 19:18:26)
     * @param el conexp.core.LatticeElement
     */
    public LatticeElementPredCompareSet(LatticeElement el) {
        this.el = el;
    }


    /**
     * Insert the method's description here.
     * Creation date: (13.07.01 19:17:34)
     * @return conexp.core.compareutils.KeyValuePair
     * @param index int
     */
    public KeyValuePair get(int index) {
        LatticeElement pred = el.getPred(index);
        return new KeyValuePair(pred.getAttribs(), pred);
    }


    /**
     * Insert the method's description here.
     * Creation date: (13.07.01 19:17:34)
     * @return int
     */
    public int getSize() {
        return el.getPredCount();
    }
}