package conexp.experimenter.framework;

import java.util.ArrayList;

/**
 * Insert the type's description here.
 * Creation date: (21.07.01 17:41:16)
 * @author
 */
public class RelationSequenceSet {
    protected ArrayList relSequences;

    /**
     * Insert the method's description here.
     * Creation date: (21.07.01 17:43:10)
     */
    public RelationSequenceSet() {
        relSequences = new ArrayList();
    }


    /**
     * Insert the method's description here.
     * Creation date: (21.07.01 17:42:36)
     * @param relSeq conexp.core.experimenter.framework.RelationSequence
     */
    public void addRelationSequence(RelationSequence relSeq) {
        relSequences.add(relSeq);
    }


    /**
     * Insert the method's description here.
     * Creation date: (21.07.01 17:41:16)
     * @return conexp.core.BinaryRelation
     * @param i int
     */
    public RelationSequence getRelationSequence(int i) {
        return (RelationSequence) relSequences.get(i);
    }


    /**
     * Insert the method's description here.
     * Creation date: (21.07.01 17:41:16)
     * @return int
     */
    public int getRelationSequenceCount() {
        return relSequences.size();
    }
}