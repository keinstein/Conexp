package conexp.core.compareutils;

import conexp.core.ConceptsCollection;
import conexp.core.ItemSet;

/**
 * Insert the type's description here.
 * Creation date: (13.07.01 19:21:57)
 * @author
 */
public class ConceptCollectionCompareSet implements ICompareSet {
    protected final ConceptsCollection coll;

    /**
     * Insert the method's description here.
     * Creation date: (13.07.01 19:24:22)
     * @param coll conexp.core.ConceptsCollection
     */
    public ConceptCollectionCompareSet(ConceptsCollection coll) {
        this.coll = coll;
    }


    /**
     * Insert the method's description here.
     * Creation date: (13.07.01 19:21:57)
     * @return conexp.core.compareutils.KeyValuePair
     * @param index int
     */
    public KeyValuePair get(int index) {
        ItemSet con = coll.conceptAt(index);
        return new KeyValuePair(con.getAttribs(), con);
    }


    /**
     * Insert the method's description here.
     * Creation date: (13.07.01 19:21:57)
     * @return int
     */
    public int getSize() {
        return coll.conceptsCount();
    }
}