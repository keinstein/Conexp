package conexp.experimenter.relationsequences;

import conexp.experimenter.framework.RelationSequence;

/**
 * Insert the type's description here.
 * Creation date: (28.07.01 23:47:21)
 * @author
 */
public class TransposeRelationSequenceDecorator extends BaseRelationGenerationStrategy {
    protected conexp.experimenter.framework.RelationSequence sourceSequence;

    /**
     * Insert the method's description here.
     * Creation date: (28.07.01 23:50:39)
     * @param sourceSequence conexp.core.experimenter.framework.RelationSequence
     */
    public TransposeRelationSequenceDecorator(RelationSequence sourceSequence) {
        super(sourceSequence.getRelationCount());
        this.sourceSequence = sourceSequence;
        createRelations();
    }


    /**
     * Insert the method's description here.
     * Creation date: (28.07.01 23:47:21)
     * @return java.lang.String
     */
    public String describeStrategy() {
        return "Transposed " + sourceSequence.describeStrategy();
    }


    /**
     * Insert the method's description here.
     * Creation date: (28.07.01 23:49:49)
     * @return conexp.core.BinaryRelation
     * @param relNo int
     */
    public conexp.core.BinaryRelation makeRelation(int relNo) {
        return conexp.core.BinaryRelationUtils.makeTransposedRelation(sourceSequence.getRelation(relNo));
    }
}